package cn.edu.nju.software.algorithm.kmeans;

import cn.edu.nju.software.pinpoint.statistics.entity.ClassNode;
import cn.edu.nju.software.pinpoint.statistics.entity.MethodNode;
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

    public GraphUtil doStaticAnalysis(String compressFile)throws Exception{
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
//            System.out.println(entry.getValue().getName());
            nodeList.add(entry.getValue().getName());
        }

        List<EData> edgeList = new ArrayList<>();
        for (Map.Entry<String, StaticCallInfo> entry : classEdges.entrySet()) {
            EData eData = new EData(entry.getValue().getCaller(),entry.getValue().getCallee(),entry.getValue().getCount());
            edgeList.add(eData);
        }

        String[] vexs = nodeList.toArray(new String[nodeList.size()]);
        EData[] edges = edgeList.toArray(new EData[edgeList.size()]);

//        for(int i =0;i<vexs.length;i++){
//            System.out.println(vexs[i]);
//        }
//        for(int i =0;i<edges.length;i++){
//            System.out.println(edges[i].getStart()+" "+edges[i].getWeight()+" "+edges[i].getEnd());
//        }
        GraphUtil pG = new GraphUtil(vexs, edges);
        return pG;
    }

    public static void main(String[] args) throws  Exception{
        StaticAnalysis analysis = new StaticAnalysis();
        analysis.doStaticAnalysis("/Users/yaya/Desktop/dddsample.jar");
//        System.out.println(Integer.MAX_VALUE);

    }

}
