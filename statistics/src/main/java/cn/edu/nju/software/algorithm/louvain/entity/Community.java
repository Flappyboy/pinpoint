package cn.edu.nju.software.algorithm.louvain.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Auther: yaya
 * @Date: 2019/5/10 10:16
 * @Description:
 */
@Setter
@Getter
public class Community {
    private int communityId;
    private List<ClassNodeInfo> interfaces;
    private List<ClassNodeInfo> allClasses;

}
