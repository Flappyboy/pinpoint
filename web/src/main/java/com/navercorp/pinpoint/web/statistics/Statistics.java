package com.navercorp.pinpoint.web.statistics;

import com.mysql.jdbc.StringUtils;
import com.navercorp.pinpoint.web.vo.callstacks.Record;
import com.navercorp.pinpoint.web.vo.callstacks.RecordSet;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class Statistics {

    private Set<String>  packagenameSet = new HashSet<>();

    public Statistics(List<String> packagenameList) {
        this.packagenameSet.addAll(packagenameList);
    }

    Map<String, Method> methodMap = new HashMap<>();
    Map<String, Call> callMap = new HashMap<>();

    public class Call{
        Method caller;
        Method callee;
        String id;
        Long count = 0l;

        public Call(Method callee, Method caller) {
            this.caller = caller;
            this.callee = callee;
            this.id = caller.getName()+";"+callee.getName();
        }
        public Call(Method callee, Method caller, Long count) {
            this.caller = caller;
            this.callee = callee;
            this.id = caller.getName()+";"+callee.getName();
            this.count = count;
        }

        @Override
        public String toString() {
            return caller.getClassName() + "," + callee.getClassName() + "," + count;
        }

        public String toClass(){return caller.getClassName() + "," + callee.getClassName() + "," + count;}

        public String toMethod(){return caller.getName() + "," + callee.getName() + "," + count;}

        public DynamicCallInfo toDynamicCallInfoForClass(String analysisId){
            DynamicCallInfo dynamicCallInfo = new DynamicCallInfo();
            dynamicCallInfo.setDynamicanalysisinfoid(analysisId);
            dynamicCallInfo.setType(0);
            dynamicCallInfo.setCaller(caller.getClassName());
            dynamicCallInfo.setCallee(callee.getClassName());
            dynamicCallInfo.setCount(Math.toIntExact(count));
            return dynamicCallInfo;
        }

        public void call(){
            count++;
        }

        public Method getCaller() {
            return caller;
        }

        public Method getCallee() {
            return callee;
        }

        public String getId() {
            return id;
        }

        public Long getCount() {
            return count;
        }
    }

    public class Method{
        private String name;

        public Method(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public String getClassName(){
            String[] list = name.split("\\(");
            if(list.length==1)
                return name;
            int i = list[0].lastIndexOf(".");
            if(i == -1)
                return list[0];
            return list[0].substring(0,i);

        }

        public String getName() {
            return name;
        }
    }

    private Method getMethod(String api){
        Method method = methodMap.get(api);
        if(method!=null)
            return method;
        method = new Method(getMethodName(api));
        methodMap.put(api, method);
        return method;
    }

    private Call getCall(String api, String parentApi){
        Call call = callMap.get(api+"&&"+parentApi);
        if(call!=null)
            return call;
        call = new Call(getMethod(api),getMethod(parentApi));
        callMap.put(api+"&&"+parentApi, call);
        return call;
    }

    public void addCall(String api, String parentApi){
        if(api==null)
            api = "";
        if(parentApi==null)
            parentApi = "";
        String[] list = api.split(":");
        if (list.length==1||list.length>=3)
            return;
        String[] list2 = parentApi.split(":");
        if (list2.length==1||list2.length>=3)
            return;
        api = list[0];
        parentApi = list2[0];
        getCall(api,parentApi).call();
    }

    public void call(Record record, Record parentRecord){
        String api = record.getFullApiDescription();
        String parentApi = null;
        if (parentRecord!=null)
            parentApi = parentRecord.getFullApiDescription();
        addCall(api,parentApi);

//        System.out.println("api:          "+api);
//        System.out.println("parent api:   "+api);
    }

    public void statisticsRecord (RecordSet recordSet){
        List<Record> recordList = recordSet.getRecordList();
        Map<Integer, Record> recordMap = new HashMap<>();
        for(Record record: recordList){
            recordMap.put(record.getId(),record);
        }
        for(Record record: recordList){
            if(!check(getClassName(record)))
                continue;
            Record parentRecord = recordMap.get(record.getParentId());
            Boolean tag = check(getClassName(parentRecord));
            while (!tag){
                if (parentRecord==null)
                    break;
                parentRecord = recordMap.get(parentRecord.getParentId());
                if (parentRecord==null)
                    break;
                tag = check(getClassName(parentRecord));
            }
            if (parentRecord==null)
                continue;
            this.call(record,parentRecord);
        }
    }
    private String getClassName(Record record){
        String methodName = getMethodName(record);
        int bracketIndex = methodName.indexOf("(");
        if(bracketIndex!=-1){
            int dotIndex = methodName.lastIndexOf(".", bracketIndex);
            if(dotIndex!=-1) {
                return methodName.substring(0, dotIndex);
            }
        }
        return methodName;
    }
    private String getMethodName(Record record){
        if (record==null)
            return "";
        String api = record.getFullApiDescription();
        if (api==null)
            return "";
        String[] list = api.split(":");
        if (list.length==1||list.length>=3)
            return "";
        return list[0];
    }

    private Boolean check(String className){
        if(this.packagenameSet.contains(className)){
            return true;
        }
        return false;
    }

    public void saveClass(){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("E:\\workspace\\project\\pinpoint\\statics-class-"+""+".txt");
            for (Map.Entry<String, Statistics.Call> entry: callMap.entrySet()){
                fileWriter.write(entry.getValue().toClass()+"\n");
            }
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveMethod(){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("E:\\workspace\\project\\pinpoint\\statics-method-"+""+".txt");
            for (Map.Entry<String, Statistics.Call> entry: callMap.entrySet()){
                fileWriter.write(entry.getValue().toMethod()+"\n");
            }
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public String sendPre(String api){
//        Map<String, Object> param = new HashMap<>();
//        DynamicCallInfo dynamicCallInfo = new DynamicCallInfo();
//        param.put("dynamicAnalysisInfo",dynamicCallInfo);
//        return doPost(api,param);
//    }

    public void sendMethod(String api){

    }

    public List<DynamicCallInfo> allCallsForDynamicCallInfo(String analysisId){
        List<DynamicCallInfo> list = new ArrayList<>();
        for (Map.Entry<String, Statistics.Call> entry: callMap.entrySet()){
            list.add(entry.getValue().toDynamicCallInfoForClass(analysisId));
        }
        return list;
    }

    private String getMethodName(String fullName){
        String[] list = fullName.split("\\(");
        if(list.length!=2 || !list[1].endsWith(")"))
            return fullName;
        String[] list2 = list[1].substring(0,list[1].length()-1).split(", ");
        StringBuilder sb = new StringBuilder();
        for(String str: list2){
            String[] para = str.split(" ");
            sb.append(para[0]).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        return list[0]+"("+sb+")";
    }

    public static void main(String[] args) {
        String str = " a b";
        String[] strList = str.split(" ");
        System.out.println(strList);
    }
}
