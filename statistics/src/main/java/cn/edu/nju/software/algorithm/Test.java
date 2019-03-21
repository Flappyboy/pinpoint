package cn.edu.nju.software.algorithm;

//测试算法

import cn.edu.nju.software.algorithm.kmeans.GraphUtil;
import cn.edu.nju.software.algorithm.kmeans.Kmeans;
import cn.edu.nju.software.algorithm.kmeans.StaticAnalysis;
import cn.edu.nju.software.algorithm.kmeans.VNode;

import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception{
        StaticAnalysis analysis = new StaticAnalysis();
        GraphUtil pG = analysis.doStaticAnalysis("/Users/yaya/Desktop/dddsample.jar");

        String[] point = {"Aa", "Bb"};//中心点
        int key = point.length;//中心点个数

        Kmeans kmeans = new Kmeans(pG,key,point);//kmeans算法
        List<GraphUtil> graphs = kmeans.run();//运行算法

        //结果打印
        System.out.println("");
        System.out.println("打印结果：");
        int i = 1;
        for(GraphUtil graphUtil:graphs){
            System.out.println("第"+i+"类：");
            printGraph(graphUtil);
            i++;
        }
    }

    public static void printGraph(GraphUtil graphUtil){

        VNode[] nodes = graphUtil.getmVexs();
        for(int i=0;i<nodes.length;i++) {
            System.out.println(nodes[i].getData());

        }

    }
}
