package cn.edu.nju.software.algorithm.mstcluster;

import cn.edu.nju.software.algorithm.kmeans.Initialization;
import cn.edu.nju.software.algorithm.kmeans.StaticAnalysis;
import cn.edu.nju.software.git.GitUtil;
import cn.edu.nju.software.git.entity.GitCommitFileEdge;

import java.util.*;

import static cn.edu.nju.software.git.GitDataUtil.getCommitFileGraph;

public class Test {
    public static void main(String[] args) throws Exception{
        Map<String, GitCommitFileEdge> map = getCommitFileGraph(GitUtil.getLocalCommit("D:\\SDA\\dddsample-core"), "D:\\SDA\\dddsample-core");

        Set<Component> components = new HashSet<>(MSTCluster.clusterWithSplit(MST.calcMST(MST.getEdges(map)), 100,3));
        System.out.println("components.size = " + components.size());
        StaticAnalysis sa = new StaticAnalysis();
        for (Component cpt : components){
            System.out.println("*******************************one components "+cpt.getNodes().size()+"******************************");
            for (ClassNode node: cpt.getNodes()){
                System.out.println(sa.changeName(node.getClassName(),'.', 3));
            }
        }

//        String[] point = Initialization.findCenter("D:\\SDA\\dddsample-core",30,5);
//        for(int i=0; i<point.length; i++){
//            System.out.println(point[i]);
//        }

//        PriorityQueue<Integer> list = new PriorityQueue<>(new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return o2-o1;
//            }
//        });
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        while (!list.isEmpty()){
//            System.out.println(list.peek());
//            list.poll();
//        }
//        String name = "xyz\\SDA\\ddd\\src";
//        String xyz = "hohohohoh";
//        String xx = xyz.replace("t", "l");
//        String yy = name.replaceAll("\\\\", "/");
//        System.out.println(yy);
//        System.out.println(xx);
    }
}
