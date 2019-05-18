package cn.edu.nju.software.algorithm.louvain;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: yaya
 * @Date: 2019/4/27 21:03
 * @Description:
 */
@Setter
@Getter
public class NodeType {
    private String name;
    private int flag;

    public NodeType(String name, int flag) {
        this.name = name;
        this.flag = flag;
    }
}
