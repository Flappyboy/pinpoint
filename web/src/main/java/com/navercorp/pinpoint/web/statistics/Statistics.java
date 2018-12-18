package com.navercorp.pinpoint.web.statistics;

import com.mysql.jdbc.StringUtils;
import com.navercorp.pinpoint.web.vo.callstacks.Record;
import com.navercorp.pinpoint.web.vo.callstacks.RecordSet;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Statistics {

    private String packagename ="";

    public Statistics(String packagename) {
        if (packagename!=null)
            this.packagename = packagename;
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
            if(!check(getMethodName(record)))
                continue;
            Record parentRecord = recordMap.get(record.getParentId());
            Boolean tag = check(getMethodName(parentRecord));
            while (!tag){
                if (parentRecord==null)
                    break;
                parentRecord = recordMap.get(parentRecord.getParentId());
                if (parentRecord==null)
                    break;
                tag = check(getMethodName(parentRecord));
            }
            if (parentRecord==null)
                continue;
            this.call(record,parentRecord);
        }
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
        if(className.startsWith(packagename)){
            return true;
        }
        return false;
    }

    public void saveClass(){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("E:\\workspace\\project\\pinpoint\\statics-class-"+packagename+".txt");
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
            fileWriter = new FileWriter("E:\\workspace\\project\\pinpoint\\statics-method-"+packagename+".txt");
            for (Map.Entry<String, Statistics.Call> entry: callMap.entrySet()){
                fileWriter.write(entry.getValue().toMethod()+"\n");
            }
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
