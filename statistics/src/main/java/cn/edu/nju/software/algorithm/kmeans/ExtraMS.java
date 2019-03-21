package cn.edu.nju.software.algorithm.kmeans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ExtraMS {
    public static HashSet<String> getExtraMS(String[] allClassNode, EData[] allEdges){
        HashSet<String> graphNodes = new HashSet<>();
        for (int i=0; i<allEdges.length; i++){
            graphNodes.add(allEdges[i].getStart());
            graphNodes.add(allEdges[i].getEnd());
        }
        HashSet<String> Nodes = new HashSet<>();
        for (int i=0; i<allClassNode.length; i++){
            Nodes.add(allClassNode[i]);
        }
        HashSet<String> extraNodes = new HashSet<>();
        extraNodes.addAll(Nodes);
        extraNodes.removeAll(graphNodes);

        return extraNodes;
    }
}
