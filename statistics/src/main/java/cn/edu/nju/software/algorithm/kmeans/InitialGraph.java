package cn.edu.nju.software.algorithm.kmeans;

import cn.edu.nju.software.pinpoint.statistics.entity.App;
import cn.edu.nju.software.pinpoint.statistics.entity.ClassNode;
import cn.edu.nju.software.pinpoint.statistics.service.impl.AppServiceImpl;
import cn.edu.nju.software.pinpoint.statistics.service.impl.ClassNodeServiceImpl;
import cn.edu.nju.software.pinpoint.statistics.service.impl.DynamicCallServiceImpl;
import cn.edu.nju.software.pinpoint.statistics.service.impl.StaticCallServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InitialGraph {

    public static String[] getAllClassNode(String appid){
        List<ClassNode> nodes = new ClassNodeServiceImpl().findByAppid(appid);
        String[] allClassNode = new String[nodes.size()];
        for(int i=0;i<nodes.size();i++){
            allClassNode[i] = nodes.get(i).getName();
        }
        return allClassNode;
    }

    public static EData[] getAllEdges(String appid, String dynamicAnalysisInfoId, int page, int pageSize, int type){
        List<HashMap<String, String>> statisticEdges = new StaticCallServiceImpl().findEdgeByAppId(appid,page,pageSize,type);
        List<HashMap<String, String>> dynamicEdges = new DynamicCallServiceImpl().findEdgeByAppId(dynamicAnalysisInfoId,page,pageSize,type);

        List<HashMap<String, String>> Edges = IntegrationEdges(statisticEdges, dynamicEdges);

        EData[] allEdges = new EData[Edges.size()];
        for (int i=0; i<Edges.size(); i++){
            String caller = Edges.get(i).get("caller");
            String callee = Edges.get(i).get("callee");
            double weight = Double.parseDouble(Edges.get(i).get("count"));
            allEdges[i].setStart(caller);
            allEdges[i].setEnd(callee);
            allEdges[i].setWeight(weight);
        }
        return allEdges;
    }

    private static List<HashMap<String, String>> IntegrationEdges(List<HashMap<String, String>> statisticEdges, List<HashMap<String, String>> dynamicEdges){
        for (int i=1; i<statisticEdges.size(); i++){
            String caller = statisticEdges.get(i).get("caller");
            String callee = statisticEdges.get(i).get("callee");
            String count = statisticEdges.get(i).get("count");
            for (int j=0; j<i; j++){
                String caller1 = statisticEdges.get(j).get("caller");
                String callee1 = statisticEdges.get(j).get("callee");
                if(caller.equals(callee1)&&callee.equals(caller1)){
                    double temp1 = Double.parseDouble(count);
                    double temp2 = Double.parseDouble(statisticEdges.get(j).get("count")) + temp1;
                    String temp = String.valueOf(temp2);
                    statisticEdges.get(j).put("count", temp);
                    statisticEdges.remove(i);
                    break;
                }
            }
        }
        for (int i=0; i<dynamicEdges.size(); i++){
            String caller = dynamicEdges.get(i).get("caller");
            String callee = dynamicEdges.get(i).get("callee");
            String count = dynamicEdges.get(i).get("count");
            for (int j=0; j<statisticEdges.size(); j++){
                String caller1 = statisticEdges.get(j).get("caller");
                String callee1 = statisticEdges.get(j).get("callee");
                if((caller.equals(caller1)&&callee.equals(callee1))||(caller.equals(callee1)&&callee.equals(caller1))){
                    statisticEdges.get(j).put("count", count);
                    break;
                }
            }
        }
        return statisticEdges;
    }

}
