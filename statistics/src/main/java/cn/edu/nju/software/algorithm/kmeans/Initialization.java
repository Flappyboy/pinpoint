package cn.edu.nju.software.algorithm.kmeans;

import cn.edu.nju.software.algorithm.mstcluster.ClassNode;
import cn.edu.nju.software.algorithm.mstcluster.Component;
import cn.edu.nju.software.algorithm.mstcluster.MST;
import cn.edu.nju.software.algorithm.mstcluster.MSTCluster;
import cn.edu.nju.software.git.GitUtil;
import cn.edu.nju.software.git.entity.GitCommitFileEdge;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

import static cn.edu.nju.software.git.GitDataUtil.getCommitFileGraph;

@Getter
@Setter
@ToString

public class Initialization {

    public static String[] findCenter(String path, int splitThreshold, int numServices) throws Exception{
        Map<String, GitCommitFileEdge> map = getCommitFileGraph(GitUtil.getLocalCommit(path));

        Set<Component> components = new HashSet<>(MSTCluster.clusterWithSplit(MST.calcMST(MST.getEdges(map)), splitThreshold,numServices));

        List<String> list = new ArrayList<>();
        for (Component cmp : components){
            list.add(calcCenter(cmp));
        }
        String[] centerPoints = new String[list.size()];
        for (int i=0; i<list.size(); i++){
            String pth = new String(list.get(i));
            String temp[] = pth.trim().split("\\.");
            int t = temp.length;
            centerPoints[i] = new String(temp[(t-1)]);
        }
        return centerPoints;
    }

    private static String calcCenter(Component cmp){
        int max = 0;
        int flag = 0;
        List<ClassNode> nodes = cmp.getNodes();
        for (int i=0; i<nodes.size();i++){
            if(nodes.get(i).getDegree() > max){
                max = nodes.get(i).getDegree();
                flag = i;
            }
        }
        return nodes.get(flag).getClassName();
    }
}
