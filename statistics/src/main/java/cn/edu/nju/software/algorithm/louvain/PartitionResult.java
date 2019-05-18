package cn.edu.nju.software.algorithm.louvain;

import cn.edu.nju.software.algorithm.louvain.entity.Community;
import cn.edu.nju.software.git.entity.GitCommitFileEdge;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @Auther: yaya
 * @Date: 2019/5/6 09:46
 * @Description:
 */
@Setter
@Getter
@ToString
public class PartitionResult {
    private int communityCount;
    private Map<String, GitCommitFileEdge> edgeMap;
    private Map<String,Integer> nodeMap;//名称：权重
    private List<Community> communityList;//社区列表
}
