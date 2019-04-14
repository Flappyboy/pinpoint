package cn.edu.nju.software.algorithm.mstcluster;

import cn.edu.nju.software.algorithm.kmeans.StaticAnalysis;
import cn.edu.nju.software.git.entity.GitCommitFileEdge;
import cn.edu.nju.software.pinpoint.statistics.dao.DynamicCallInfoMapper;
import cn.edu.nju.software.pinpoint.statistics.entity.DynamicCallInfo;
import cn.edu.nju.software.pinpoint.statistics.entity.PartitionInfo;
import cn.edu.nju.software.pinpoint.statistics.entity.bean.EdgeBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InitialEdges {
    //获取动态数据
//    public static List<GitCommitFileEdge> getAllDynamicEdge(PartitionInfo partitionInfo){
//        String dynamicanalysisinfoid = partitionInfo.getDynamicanalysisinfoid();
//        int type = partitionInfo.getType();
//        HashMap<String, Integer> nodeKeys = new HashMap<String, Integer>();
//        List<DynamicCallInfo> dynamicCallInfos = new ArrayList<>();
//        int key = 0;
//        if (dynamicanalysisinfoid != null) {
//            dynamicCallInfos = DynamicCallInfoFromDB.getDynamicCallInfos(partitionInfo);
//            for (DynamicCallInfo dynamicCallInfo : dynamicCallInfos) {
//                if (nodeKeys.get(dynamicCallInfo.getCaller()) == null) {
//                    key += 1;
//                    nodeKeys.put(dynamicCallInfo.getCaller(), key);
//                }
//                if (nodeKeys.get(dynamicCallInfo.getCallee()) == null) {
//                    key += 1;
//                    nodeKeys.put(dynamicCallInfo.getCallee(), key);
//                }
//            }
//        }
//
//        //获取动态数据边
//        HashMap<String, EdgeBean> edges = new HashMap<String, EdgeBean>();
//        List<GitCommitFileEdge> dynamicEdges = new ArrayList<>();
//        StaticAnalysis sa = new StaticAnalysis();
//        if (dynamicCallInfos != null && dynamicCallInfos.size() > 0) {
//            for (DynamicCallInfo dynamicCallInfo : dynamicCallInfos) {
//                String edgeKey = dynamicCallInfo.getCaller() + "_" + dynamicCallInfo.getCallee();
//                System.out.println(edgeKey);
//                EdgeBean edge = edges.get(edgeKey);
//                if (edge == null) {
//                    EdgeBean newedge = new EdgeBean();
//                    GitCommitFileEdge tempEdge = new GitCommitFileEdge();
//                    newedge.setSourceId(dynamicCallInfo.getCaller());
//                    String src = ClassNodeFromDB.getClassNameById(dynamicCallInfo.getCaller());
//                    tempEdge.setSourceName(src);
//                    int sourceKey = nodeKeys.get(dynamicCallInfo.getCaller());
//                    newedge.setSourceKey(sourceKey);
//                    newedge.setTargetId(dynamicCallInfo.getCallee());
//                    String dest = ClassNodeFromDB.getClassNameById(dynamicCallInfo.getCallee());
//                    tempEdge.setSourceName(dest);
//                    int targetKey = nodeKeys.get(dynamicCallInfo.getCallee());
//                    newedge.setTargetKey(targetKey);
//                    newedge.setWeight(dynamicCallInfo.getCount());
//                    tempEdge.setCount(dynamicCallInfo.getCount());
//                    edges.put(edgeKey, newedge);
//                    dynamicEdges.add(tempEdge);
//                } else {
//                    int dyCount = dynamicCallInfo.getCount();
//                    int stCount = edge.getWeight();
//                    edge.setWeight(dyCount + stCount);
//                    edges.put(edgeKey, edge);
//                }
//            }
//        }
//        return dynamicEdges;
//    }

    public static List<GitCommitFileEdge> getAllDynamicEdge(PartitionInfo partitionInfo){
        String dynamicanalysisinfoid = partitionInfo.getDynamicanalysisinfoid();
        List<DynamicCallInfo> dynamicCallInfos = new ArrayList<>();
        List<GitCommitFileEdge> dynamicEdges = new ArrayList<>();
        if (dynamicanalysisinfoid != null) {
            dynamicCallInfos = DynamicCallInfoFromDB.getDynamicCallInfos(partitionInfo);
            if (dynamicCallInfos != null && dynamicCallInfos.size() > 0){
                for (DynamicCallInfo dynamicCallInfo : dynamicCallInfos) {
                    GitCommitFileEdge tempEdge = new GitCommitFileEdge();
                    String src = ClassNodeFromDB.getClassNameById(dynamicCallInfo.getCaller());
                    tempEdge.setSourceName(src);
                    String dest = ClassNodeFromDB.getClassNameById(dynamicCallInfo.getCallee());
                    tempEdge.setTargetName(dest);
                    tempEdge.setCount(dynamicCallInfo.getCount());
                    dynamicEdges.add(tempEdge);
                }
            }
        }
        return dynamicEdges;
    }
}
