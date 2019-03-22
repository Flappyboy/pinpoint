package cn.edu.nju.software.algorithm.kmeans;

import cn.edu.nju.software.pinpoint.statistics.entity.ClassNode;
import cn.edu.nju.software.pinpoint.statistics.entity.StaticCallInfo;
import cn.edu.nju.software.pinpoint.statistics.utils.FileUtil;
import cn.edu.nju.software.pinpoint.statistics.utils.asm.ClassAdapter;
import cn.edu.nju.software.pinpoint.statistics.utils.asm.MethodAdapter;
import cn.edu.nju.software.pinpoint.statistics.utils.file.FileCompress;
import cn.edu.nju.software.pinpoint.statistics.utils.louvain.Edge;
import org.springframework.asm.ClassReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaticAnalysis {

    public GraphUtil doStaticAnalysis(String compressFile) throws Exception {
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
        for (Map.Entry<String, ClassNode> entry : classNodes.entrySet()) {
            System.out.println(entry.getValue().getName());
            String className = entry.getValue().getName();
            String defaultName = changeName(className,'.',3);
            nodeList.add(defaultName);
        }

        List<EData> edgeList = new ArrayList<>();
        Map<String,EData> edgeMap = new HashMap<>();
        for (Map.Entry<String, StaticCallInfo> entry : classEdges.entrySet()) {
            String caller = entry.getValue().getCaller();
            String callerDefaultName = changeName(caller,'.',3);
            String callee = entry.getValue().getCallee();
            String calleeDefaultName = changeName(callee,'.',3);
            int count = entry.getValue().getCount();

            String key1 = callerDefaultName+"-"+calleeDefaultName;
            String key2 = calleeDefaultName+"-"+callerDefaultName;

            if((!edgeMap.containsKey(key1))&&(!edgeMap.containsKey(key2))){
                System.out.println(key1);
                edgeMap.put(key1,new EData(callerDefaultName,calleeDefaultName,count));
            }else{
                if(edgeMap.containsKey(key1)) {
                    EData eData = edgeMap.get(key1);
                    eData.setWeight(eData.getWeight()+count);
                    edgeMap.put(key1,eData);
                }
                if(edgeMap.containsKey(key2)) {
                    EData eData = edgeMap.get(key2);
                    eData.setWeight(eData.getWeight()+count);
                    edgeMap.put(key2,eData);
                }
            }
        }

//        System.out.println("原来边数：   "+classEdges.size());
//        System.out.println("合并后边数：   "+edgeMap.size());
        for(Map.Entry<String, EData> entry:edgeMap.entrySet()){
            EData eData = entry.getValue();
            double weight = eData.getWeight();
            double newWeight = 1.0 / weight;
            eData.setWeight(newWeight);
            System.out.println(eData.toString());
            edgeList.add(eData);
        }

        String[] vexs = nodeList.toArray(new String[nodeList.size()]);
        EData[] edges = edgeList.toArray(new EData[edgeList.size()]);

        GraphUtil pG = new GraphUtil(vexs, edges);
        return pG;
    }

    // 查找字符 最后第几次出现的位置
    public int lastIndexLetter(String str, char ch, int lin) {
        char[] array = str.toCharArray();
        for (int i = array.length - 1; i > -1; i--) {
            if (array[i] == ch && --lin == 0) {
                return i;
            }
        }
        return -1;

    }

    public String changeName(String str, char ch, int lin){
        int index = lastIndexLetter(str,'.',3);
        String defaultName = str.substring(index+1);
        return defaultName;
    }


    public static void main(String[] args) throws Exception {
        StaticAnalysis analysis = new StaticAnalysis();
        analysis.doStaticAnalysis("/Users/yaya/Desktop/dddsample.jar");
//        System.out.println(Integer.MAX_VALUE);
//        String s = "se.citerus.dddsample.domain.shared.ValueObject";
//        int i = analysis.lastIndexLetter("se.citerus.dddsample.domain.shared.ValueObject", '.', 3);
//        System.out.println(i);
//        System.out.println(analysis.changeName(s,'.',3));

//        double x = 1.0/5.0;
//        System.out.println(x);
    }


}
