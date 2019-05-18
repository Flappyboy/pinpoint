package cn.edu.nju.software.algorithm;

//测试算法

import cn.edu.nju.software.algorithm.kmeans.*;

import java.util.List;
import java.util.Set;

public class Test {
    public static void main(String[] args) throws Exception{

        //获取中心点
        //path为本地git信息路径"/xxx/xxx/.git"
        String[] point = Initialization.findCenter("D:\\SDA\\dddsample-core", 100, 5);//中心点
//        String[] point = Initialization.findCenter("/Users/yaya/Desktop/dddsample-core", 100, 5);//中心点
        System.out.println("中心点：");
        for(int i =0;i<point.length;i++){
            System.out.println(point[i]);
        }
        System.out.println("");

        StaticAnalysis analysis = new StaticAnalysis();
        //静态分析结果构成图结构
        GraphUtil pG = analysis.doStaticAnalysis("D:\\SDA\\dddsample-core\\out\\artifacts\\dddsample_jar\\dddsample.jar");
//        GraphUtil pG = analysis.doStaticAnalysis("/Users/yaya/Desktop/dddsample.jar");

        int key = point.length;//中心点个数

        System.out.println("中心点个数："+key);
        System.out.println("");


        Kmeans kmeans = new Kmeans(pG,key,point);//kmeans算法
        List<Set<String>> graphs = kmeans.run();//运行算法

        //结果打印
        System.out.println("");
        System.out.println("打印结果：");
        int i = 1;
        for(Set<String> graphUtil:graphs){
            System.out.println("第"+i+"类：");
            for(String mypoint : graphUtil)
                System.out.println(mypoint);
            i++;
        }
    }

}
