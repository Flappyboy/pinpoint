package cn.edu.nju.software.algorithm.louvain;

import cn.edu.nju.software.algorithm.louvain.entity.ClassNodeInfo;
import cn.edu.nju.software.algorithm.louvain.entity.Community;
import cn.edu.nju.software.git.entity.GitCommitFileEdge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Metric {
    //稳定性计算
    public double getStability(PartitionResult partitionResult){
        Map<String, GitCommitFileEdge> edgeMap = partitionResult.getEdgeMap();
        Map<String,Integer> nodeMap = partitionResult.getNodeMap();

        double internalEdge = 0;
        double externalEdge = 0;
        for (Map.Entry<String, GitCommitFileEdge> entry : edgeMap.entrySet()) {
            GitCommitFileEdge edge = entry.getValue();
            int sourcePatition = nodeMap.get(edge.getSourceName());
            int targetPatition = nodeMap.get(edge.getTargetName());
            if(sourcePatition == targetPatition)
                internalEdge +=edge.getCount();
            else
                externalEdge +=edge.getCount();
        }
        System.out.println("内部："+internalEdge);
        System.out.println("外部："+externalEdge);
        double stability = internalEdge/(internalEdge+externalEdge);
        return stability;
    }

    //CHM
    public double getCHM(PartitionResult partitionResult){
        List<Community> communityList = partitionResult.getCommunityList();
        double CHMjSum = 0;
        double interfaceCount = 0;
        for(Community community :communityList){
            List<ClassNodeInfo> interfaces = community.getInterfaces();
            for(ClassNodeInfo classNodeInfo : interfaces){
                interfaceCount++;
                CHMjSum += classNodeInfo.calculateCHMi();
            }
        }
        return CHMjSum/interfaceCount;
    }
    //CHD
    public double getCHD(PartitionResult partitionResult){
        List<Community> communityList = partitionResult.getCommunityList();
        double CHDjSum = 0;
        double interfaceCount = 0;
        for(Community community :communityList){
            List<ClassNodeInfo> interfaces = community.getInterfaces();
            for(ClassNodeInfo classNodeInfo : interfaces){
                interfaceCount++;
                CHDjSum += classNodeInfo.calculateCHDi();
            }
        }
        return CHDjSum/interfaceCount;
    }
    //INF
    public double getINF(List<ClassNodeInfo> infs,double k){
        return infs.size()/k;
    }
    //OPN
    public double getOPN(List<ClassNodeInfo> infs){
        int sum= 0;
        for(ClassNodeInfo classNodeInfo:infs){
            sum += classNodeInfo.getOpCount();
        }
        return sum;
    }
    //IRN
    public int getIRN(Map<String, GitCommitFileEdge> edgeMap){
        int num = 0;
        for (Map.Entry<String, GitCommitFileEdge> entry : edgeMap.entrySet()) {
            GitCommitFileEdge edge = entry.getValue();
            if(isContained(edge.getSourceName(),edge.getTargetName())) {
                System.out.println(edge.toString());
                num += edge.getCount();
            }
        }
        System.out.println("num  :"+num);
        return num;
    }


    public static boolean isContained(String str1,String str2){
        HashMap<String,NodeType> map = new HashMap<>();
        NodeType n1 = new NodeType("domain.service.RoutingService",3);
        NodeType n2 = new NodeType("pathfinder.internal.GraphDAO",3);
        NodeType n3 = new NodeType("pathfinder.api.GraphTraversalService",2);
        NodeType n4 = new NodeType("dddsample.application.ApplicationEvents",4);
        NodeType n5 = new NodeType("dddsample.application.BookingService",1);
        NodeType n6 = new NodeType("dddsample.application.HandlingEventService",5);
        NodeType n7 = new NodeType("dddsample.application.CargoInspectionService",1);
        NodeType n8 = new NodeType("com.aggregator.HandlingReportServiceService",0);
        NodeType n9 = new NodeType("com.aggregator.HandlingReportService",0);
        NodeType n10 = new NodeType("booking.facade.BookingServiceFacade",6);
        map.put("domain.service.RoutingService",n1);
        map.put("pathfinder.internal.GraphDAO",n2);
        map.put("pathfinder.api.GraphTraversalService",n3);
        map.put("dddsample.application.ApplicationEvents",n4);
        map.put("dddsample.application.BookingService",n5);
        map.put("dddsample.application.HandlingEventService",n6);
        map.put("dddsample.application.CargoInspectionService",n7);
        map.put("com.aggregator.HandlingReportServiceService",n8);
        map.put("com.aggregator.HandlingReportService",n9);
        map.put("booking.facade.BookingServiceFacade",n10);

        if(map.containsKey(str1)&&map.containsKey(str2)){
//            if(map.get(str1).getFlag() != map.get(str2).getFlag())
            return true;
//            else
//                return false;
        }else
            return false;
    }


}
