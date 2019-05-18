package cn.edu.nju.software.algorithm.louvain;

import cn.edu.nju.software.algorithm.mstcluster.InitialEdges;
import cn.edu.nju.software.algorithm.mstcluster.MST;
import cn.edu.nju.software.algorithm.mstcluster.PartitionInfoFromDB;
import cn.edu.nju.software.algorithm.normalization.Normalize4Scale;
import cn.edu.nju.software.algorithm.normalization.NormalizeEdge;
import cn.edu.nju.software.git.GitDataUtil;
import cn.edu.nju.software.git.GitUtil;
import cn.edu.nju.software.git.entity.GitCommitFileEdge;
import cn.edu.nju.software.pinpoint.statistics.entity.ClassNode;
import cn.edu.nju.software.pinpoint.statistics.entity.PartitionInfo;
import cn.edu.nju.software.pinpoint.statistics.entity.StaticCallInfo;
import cn.edu.nju.software.pinpoint.statistics.utils.FileUtil;
import cn.edu.nju.software.pinpoint.statistics.utils.asm.ClassAdapter;
import cn.edu.nju.software.pinpoint.statistics.utils.asm.MethodAdapter;
import cn.edu.nju.software.pinpoint.statistics.utils.file.FileCompress;
import cn.edu.nju.software.pinpoint.statistics.utils.louvain.LouvainUtil;
import org.springframework.asm.ClassReader;
import org.springframework.cglib.reflect.MethodDelegate;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

//commit数据+静态数据+动态数据
public class AllDataLouvainCluster {

    public static void main(String[] args) throws Exception {
        //获取来自git的所有边
        Map<String, GitCommitFileEdge> map = GitDataUtil.getCommitFileGraph(GitUtil.getLocalCommit("/Users/yaya/Desktop/pinpoint/data/code/dddsample-core"), "/Users/yaya/Desktop/pinpoint/data/code/dddsample-core");
        List<GitCommitFileEdge> gEdges = MST.getEdges(map);
        for (GitCommitFileEdge e : gEdges) {
            String src = changeName(e.getSourceName(), '.', 3);
            e.setSourceName(src);
            String dest = changeName(e.getTargetName(), '.', 3);
            e.setTargetName(dest);
//            System.out.println(e);
        }
        System.out.println(gEdges.size());

        //获取来自静态分析得到的所有边
        List<GitCommitFileEdge> sEdges = getStatisticEdges("/Users/yaya/Desktop/pinpoint/data/jar/dddsample.jar");
        System.out.println(sEdges.size());
//        获取动态边
        PartitionInfo partitionInfo = PartitionInfoFromDB.getPartitionInfoById("190324D9D9ZGSG54");
        List<GitCommitFileEdge> dEdges = new InitialEdges().getAllDynamicEdge(partitionInfo);
        System.out.println(dEdges.size());
//      合并后的边
        Map<String, GitCommitFileEdge> edgeMap = new HashMap<>();
//      合并后的点
        Map<String, Integer> nodeMap = new HashMap<>();
        int keyId = 0;

//        放入git数据
        for (GitCommitFileEdge e : gEdges) {
            keyId = mergeNode(e, nodeMap, keyId);
            mergeEdge(e, edgeMap);
        }
//        System.out.println(edgeMap.size());
//        放入静态数据
        for (GitCommitFileEdge e : sEdges) {
            keyId = mergeNode(e, nodeMap, keyId);
            mergeEdge(e, edgeMap);
        }
//        System.out.println(edgeMap.size());
//        放入动态数据
        for (GitCommitFileEdge e : dEdges) {
            keyId = mergeNode(e, nodeMap, keyId);
            mergeEdge(e, edgeMap);
        }

//        System.out.println(edgeMap.size());
//        System.out.println(keyId);

        //归一化后的边
//        Normalize4Scale normalize4Scale = new Normalize4Scale();
//        Map<String, NormalizeEdge> newEdgeMap = normalize4Scale.excute(edgeMap);
//
//        System.out.println("归一化后数量： "+newEdgeMap.size());
//        excutLouvain(nodeMap, newEdgeMap);

        Map<String,Integer> nodePatitionMap = excutLouvain(nodeMap, edgeMap);
        PartitionResult partitionResult = new PartitionResult();
        partitionResult.setEdgeMap(edgeMap);
        partitionResult.setNodeMap(nodePatitionMap);

        // 稳定性指标
        Metric metric = new Metric();
        System.out.println("稳定性："+metric.getStability(partitionResult));
        // IRN指标
        System.out.println("num  :"+metric.getIRN(edgeMap));
    }

    /**
     * 执行louvain算法
     *
     * @param nodeMap
     * @param edgeMap
     * @throws Exception
     */
//    public static void excutLouvain(Map<String, Integer> nodeMap, Map<String, NormalizeEdge> edgeMap) throws Exception {
    public static Map<String,Integer> excutLouvain(Map<String, Integer> nodeMap, Map<String, GitCommitFileEdge> edgeMap) throws Exception {

        String edgePath = "/Users/yaya/Desktop/";
        String edgeFileName = System.currentTimeMillis() + "_edge.txt";
        FileUtil.creatFile(edgePath, edgeFileName);
        String filePath = edgePath + edgeFileName;
        List<String> lines = new ArrayList<>();
        String count = (nodeMap.size() + 1) + " " + edgeMap.size();
        lines.add(count);
        for (Map.Entry<String, GitCommitFileEdge> entry : edgeMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            GitCommitFileEdge edge = entry.getValue();
            String source = edge.getSourceName();
            String target = edge.getTargetName();
            String line = nodeMap.get(source) + " " + nodeMap.get(target) + " " + edge.getCount();
            lines.add(line);
        }
        FileUtil.writeFile(lines, filePath);
        String outputPath = edgePath + System.currentTimeMillis() + "_louvain.txt";
        LouvainUtil.execute(filePath, outputPath);

        String classPath = System.currentTimeMillis() + "_community.txt";
        Map<String,Integer> nodePartitionMap = keyToClass(nodeMap, outputPath, edgePath + classPath);
        return nodePartitionMap;
    }

    /**
     * key对应于classname输出
     *
     * @param nodeMap
     * @param inputPath
     * @param outputPath
     * @throws Exception
     */
    public static Map<String,Integer> keyToClass(Map<String, Integer> nodeMap, String inputPath, String outputPath) throws Exception {
        Map<String,Integer> nodePartitionMap = new HashMap<>();

        Map<Integer, String> keyMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : nodeMap.entrySet()) {
            System.out.println(entry.getValue() + "    " + entry.getKey());
            keyMap.put(entry.getValue(), entry.getKey());
        }
        List<String> readLines = FileUtil.readFile(inputPath);
        List<String> writelines = new ArrayList<>();
        for (int i = 1; i < readLines.size(); i++) {
            String[] keys = readLines.get(i).split(" ");
            for (int j = 0; j < keys.length; j++) {
                System.out.println(keys[j]);
                String className = keyMap.get(Integer.valueOf(keys[j]));
                System.out.println(keys[j] + "    " + className);
                writelines.add(className);
                nodePartitionMap.put(className,i);
            }
            writelines.add(" ");
        }
        FileUtil.writeFile(writelines, outputPath);
        return nodePartitionMap;
    }

    /**
     * 合并点
     *
     * @param e
     * @param nodeMap
     * @param keyId
     * @return
     */
    public static int mergeNode(GitCommitFileEdge e, Map<String, Integer> nodeMap, int keyId) {
        if (!nodeMap.containsKey(e.getSourceName())) {
            keyId++;
            nodeMap.put(e.getSourceName(), keyId);
        }
        if (!nodeMap.containsKey(e.getTargetName())) {
            keyId++;
            nodeMap.put(e.getTargetName(), keyId);
        }
        return keyId;
    }

    /**
     * 合并边
     *
     * @param e
     * @param edgeMap
     */
    public static void mergeEdge(GitCommitFileEdge e, Map<String, GitCommitFileEdge> edgeMap) {
        String key = e.getSourceName() + "-!-" + e.getTargetName();
        if (!edgeMap.containsKey(key)) {
            edgeMap.put(key, e);
        } else {
            GitCommitFileEdge oldEdge = edgeMap.get(key);
            int oldCount = oldEdge.getCount();
            oldEdge.setCount(oldCount + e.getCount());
            edgeMap.put(key, oldEdge);
        }
    }


    //修改成了单向的了，同StaticAnalysis
    public static List<GitCommitFileEdge> getStatisticEdges(String compressFile) throws Exception {
        ArrayList<String> myfiles = new ArrayList<String>();
        String path = "";
        String outPath = compressFile.trim().substring(0, compressFile.trim().lastIndexOf("."));
        System.out.println("解压路径：" + outPath);
        FileCompress.unCompress(compressFile, outPath);
        if (path.trim().endsWith(".war"))
            path = outPath + "/WEB-INF/classes";
        else
            path = outPath;

        FileUtil.traverseFolder(path, myfiles);
        System.out.println("class文件数：" + myfiles.size());
        for (String file : myfiles) {
            if (file.endsWith(".class")) {
                InputStream inputstream = new FileInputStream(new File(file));
                ClassReader cr = new ClassReader(inputstream);
                ClassAdapter ca = new ClassAdapter();
                cr.accept(ca, ClassReader.EXPAND_FRAMES);
            }
        }

        //类
        HashMap<String, ClassNode> classNodes = ClassAdapter.classNodes;
        HashMap<String, StaticCallInfo> classEdges = MethodAdapter.classEdges;

        List<String> nodeList = new ArrayList<>();
//        System.out.println("静态分析的点：  开始=======================");
        for (Map.Entry<String, ClassNode> entry : classNodes.entrySet()) {
            String className = entry.getValue().getName();
            String defaultName = changeName(className, '.', 3);
//            System.out.println(defaultName);
            nodeList.add(defaultName);
        }
//        System.out.println("静态分析的点：  结束========================");

        List<GitCommitFileEdge> edgeList = new ArrayList<>();
        Map<String, GitCommitFileEdge> edgeMap = new HashMap<>();
        for (Map.Entry<String, StaticCallInfo> entry : classEdges.entrySet()) {
            String caller = entry.getValue().getCaller();
            String callerDefaultName = changeName(caller, '.', 3);
            String callee = entry.getValue().getCallee();
            String calleeDefaultName = changeName(callee, '.', 3);
            if (classNodes.containsKey(caller) && classNodes.containsKey(callee)) {
                int count = entry.getValue().getCount();

                String key1 = callerDefaultName + "-" + calleeDefaultName;
                if ((!edgeMap.containsKey(key1))) {
                    edgeMap.put(key1, new GitCommitFileEdge(callerDefaultName, calleeDefaultName, count));
                } else {

                    GitCommitFileEdge e = edgeMap.get(key1);
                    e.setCount(e.getCount() + count);
                    edgeMap.put(key1, e);

                }
            }
        }

//        System.out.println("原来边数：   "+classEdges.size());
//        System.out.println("合并后边数：   "+edgeMap.size());
        for (Map.Entry<String, GitCommitFileEdge> entry : edgeMap.entrySet()) {
            GitCommitFileEdge e = entry.getValue();
            edgeList.add(e);
        }

        return edgeList;
    }

    // 查找字符 最后第几次出现的位置
    public static int lastIndexLetter(String str, char ch, int lin) {
        char[] array = str.toCharArray();
        for (int i = array.length - 1; i > -1; i--) {
            if (array[i] == ch && --lin == 0) {
                return i;
            }
        }
        return -1;

    }

    public static String changeName(String str, char ch, int lin) {
        int index = lastIndexLetter(str, '.', 3);
        String defaultName = str.substring(index + 1);
        return defaultName;
    }

}
