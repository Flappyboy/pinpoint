package cn.edu.nju.software.algorithm.mstcluster;

import cn.edu.nju.software.algorithm.kmeans.StaticAnalysis;
import cn.edu.nju.software.git.GitUtil;
import cn.edu.nju.software.git.entity.GitCommitFileEdge;
import cn.edu.nju.software.pinpoint.statistics.entity.PartitionInfo;
import cn.edu.nju.software.pinpoint.statistics.service.impl.PartitionServiceImpl;
import cn.edu.nju.software.sda.partition.Partition;

import java.util.*;

import static cn.edu.nju.software.git.GitDataUtil.getCommitFileGraph;

//将commit数据与静态数据粗暴累加，使用MSTCluster划分
public class AllDataMSTCluster {
    public static void main(String[] args) throws Exception{
        Map<String, GitCommitFileEdge> map = getCommitFileGraph(GitUtil.getLocalCommit("D:\\SDA\\dddsample-core"), "D:\\SDA\\dddsample-core");

        //获取来自git的所有边
        List<GitCommitFileEdge> gEdges = MST.getEdges(map);
        StaticAnalysis sa = new StaticAnalysis();
        for (GitCommitFileEdge e : gEdges){
            String src = sa.changeName(e.getSourceName(),'.', 3);
            e.setSourceName(src);
            String dest = sa.changeName(e.getTargetName(), '.', 3);
            e.setTargetName(dest);
        }
//        System.out.println("gEdges:  " + gEdges.size());
//        for (int i=0; i<10; i++){
//            System.out.println(gEdges.get(i).getSourceName()+"   &  "+gEdges.get(i).getTargetName());
//        }

        //获取来自静态分析得到的所有边
        //居然会获取到java.util.Collections这种类？？？待解决
        List<GitCommitFileEdge> sEdges = new StaticAnalysis().getStatisticEdges("D:\\SDAforAL\\dddsample.jar");
//        System.out.println("sEdges:  " + sEdges.size());
//        for (int i=0; i<sEdges.size(); i++){
//            System.out.println(sEdges.get(i).getSourceName()+"   &  "+sEdges.get(i).getTargetName());
//        }

        PartitionInfo partitionInfo = PartitionInfoFromDB.getPartitionInfoById("190324D9D9ZGSG54");
        //获取动态边
        List<GitCommitFileEdge> dEdges = new InitialEdges().getAllDynamicEdge(partitionInfo);
//        System.out.println("dEdges:  " + dEdges.size());
//        for (int i=0; i<dEdges.size(); i++){
//            System.out.println(dEdges.get(i).getSourceName()+"   &  "+dEdges.get(i).getTargetName());
//        }


        //合并动静态的边
        for (int i=0; i<sEdges.size(); i++){
            for (int j=0; j<dEdges.size(); j++){
                if(sEdges.get(i).getSourceName().equals(dEdges.get(j).getSourceName())&&
                        sEdges.get(i).getTargetName().equals(dEdges.get(j).getTargetName())){
                    sEdges.get(i).setCount(sEdges.get(i).getCount()+dEdges.get(j).getCount());
                    dEdges.remove(j);
                    break;
                }
            }
        }
        if(dEdges.size()>0){
            sEdges.addAll(dEdges);
            dEdges.clear();
        }

        //合并来自git的边与动静态分析所得的边
        for (int i=0; i<sEdges.size()&&!sEdges.isEmpty(); i++){
            for (int j=0; j<gEdges.size(); j++){
                if(sEdges.get(i).getSourceName().equals(gEdges.get(j).getSourceName())&&
                        sEdges.get(i).getTargetName().equals(gEdges.get(j).getTargetName())){
                    sEdges.get(i).setCount(sEdges.get(i).getCount()+gEdges.get(j).getCount());
                    gEdges.remove(j);
                    break;
                }
            }
        }
        if(gEdges.size()>0){
            sEdges.addAll(gEdges);
            gEdges.clear();
        }

        for (int i=0; i<sEdges.size()-1; i++){
            for (int j=i+1; j<sEdges.size(); j++){
                if((sEdges.get(i).getTargetName().equals(sEdges.get(j).getSourceName())&&
                        sEdges.get(i).getSourceName().equals(sEdges.get(j).getTargetName()))||
                        (sEdges.get(i).getSourceName().equals(sEdges.get(j).getSourceName())&&
                        sEdges.get(i).getTargetName().equals(sEdges.get(j).getTargetName()))){
                    sEdges.get(i).setCount(sEdges.get(i).getCount() + sEdges.get(j).getCount());
                    sEdges.remove(j);
                    break;
                }
            }
        }
        System.out.println(sEdges.size());


        //MSTCluster
        Set<Component> components = new HashSet<>(MSTCluster.clusterWithSplit(MST.calcMST(sEdges), 100,3));
        System.out.println("components.size = " + components.size());
        for (Component cpt : components){
            System.out.println("*******************************one components "+ cpt.getNodes().size() +"******************************");
            for (ClassNode node: cpt.getNodes()){
                System.out.println(node.getClassName());
            }
        }

    }
}
