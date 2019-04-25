package cn.edu.nju.software.algorithm.normalization;

import cn.edu.nju.software.git.entity.GitCommitFileEdge;

import java.util.HashMap;
import java.util.Map;

public class Normalize4Scale {
    public Map<String, NormalizeEdge> excute(Map<String, GitCommitFileEdge> edgeMap) {
        double maxV = Double.MIN_VALUE;
        double minV = Double.MAX_VALUE;
        for (Map.Entry<String, GitCommitFileEdge> entry : edgeMap.entrySet()) {
            GitCommitFileEdge edge = entry.getValue();
            double count = edge.getCount();
            if (count > maxV)
                maxV = count;
            if (count < minV)
                minV = count;
        }

        Map<String, NormalizeEdge> newEdgeMap = new HashMap<>();
        for (Map.Entry<String, GitCommitFileEdge> entry : edgeMap.entrySet()) {
            String key = entry.getKey();
            GitCommitFileEdge edge = entry.getValue();
            double count = edge.getCount();
            double newWeight = maxV == minV ? minV : (count - minV) / (maxV - minV);
//            edge.setCount(newWeight);
            NormalizeEdge normalizeEdge = new NormalizeEdge();
            normalizeEdge.setSourceName(edge.getSourceName());
            normalizeEdge.setTargetName(edge.getTargetName());
            normalizeEdge.setWeight(newWeight);
            newEdgeMap.put(key, normalizeEdge);
        }

        return newEdgeMap;
    }

}
