package cn.edu.nju.software.algorithm.louvain.entity;

import cn.edu.nju.software.algorithm.louvain.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: yaya
 * @Date: 2019/5/10 09:21
 * @Description:
 */
@Setter
@Getter
@ToString
public class ClassNodeInfo {
    private String fullname;
    private String name;
    private int communityId;
    private int flag; //0-class,1-interface
    private List<MethodDesc> methodDescs;
    private int opCount; // 方法个数

    private double chmi;
    private double chdi;

    public double calculateCHMi(){
        if(methodDescs.size() == 1)
            return 1;
        HashMap<String,Integer> paramMap = new HashMap<>();
        HashMap<String,Integer> retMap = new HashMap<>();
        for(MethodDesc methodDesc :methodDescs){
            List<String> params = methodDesc.getParam();
            for(String param :params)
                mapAdd(param,paramMap);
            String ret = methodDesc.getRetType();
            mapAdd(ret,retMap);
        }
        double paramIntersectionCount = 0;//交集
        double paramUnionCount = 0;//并集
        double retIntersectionCount = 0;//交集
        double retUnionCount = 0;//并集
        for(Map.Entry<String, Integer> entry : paramMap.entrySet()){
            int count = entry.getValue();
            paramUnionCount +=count;
            if(count>1)
                paramIntersectionCount+=count;
        }
        for(Map.Entry<String, Integer> entry : retMap.entrySet()){
            int count = entry.getValue();
            retUnionCount +=count;
            if(count>1)
                retIntersectionCount+=count;
        }
       return ((paramIntersectionCount/paramUnionCount+retIntersectionCount/retUnionCount)/2.0)/(opCount*(opCount-1)/2.0);
    }
    public double calculateCHDi(){
        if(methodDescs.size() == 1)
            return 1;
        HashMap<String,Integer> wordMap = new HashMap<>();
        for(MethodDesc methodDesc :methodDescs){
            String name  = methodDesc.getMethodName();
            List<String> words = StringUtils.camel2Underline(name);
            for(String word:words)
                mapAdd(word,wordMap);
        }

        double intersectionCount = 0;//交集
        double unionCount = 0;//并集
        for(Map.Entry<String, Integer> entry : wordMap.entrySet()){
            int count = entry.getValue();
            unionCount +=count;
            if(count>1)
                intersectionCount+=count;
        }
        return (intersectionCount/unionCount)/(opCount*(opCount-1)/2.0);
    }

    private void mapAdd(String key, HashMap<String,Integer> map){
        if(map.containsKey(key)){
            int count  = map.get(key);
            map.put(key,count+1);
        }else{
            map.put(key,1);
        }
    }
}
