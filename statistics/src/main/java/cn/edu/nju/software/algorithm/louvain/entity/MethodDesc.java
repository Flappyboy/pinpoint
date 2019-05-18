package cn.edu.nju.software.algorithm.louvain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @Auther: yaya
 * @Date: 2019/5/10 09:31
 * @Description:
 */
@Setter
@Getter
@ToString
public class MethodDesc {
    private int paramCount; //参数个数
    private int retCount; // 返回值个数，0-void

    private String methodName;//方法名
    private List<String> param;//参数列表
    private String retType;//返回值
}
