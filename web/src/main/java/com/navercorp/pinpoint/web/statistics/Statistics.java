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

    public String sendPre(String api){
        Map<String, Object> param = new HashMap<>();
        DynamicCallInfo dynamicCallInfo = new DynamicCallInfo();
        param.put("dynamicAnalysisInfo",dynamicCallInfo);
        return doPost(api,param);
    }

    public void sendMethod(String api){

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

    public static String doPost(String url, Map<String, Object> paramMap) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String result = "";
        // 创建httpClient实例
        httpClient = HttpClients.createDefault();
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        // 配置请求参数实例
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 设置连接主机服务超时时间
                .setConnectionRequestTimeout(35000)// 设置连接请求超时时间
                .setSocketTimeout(60000)// 设置读取数据连接超时时间
                .build();
        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);
        // 设置请求头
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        // 封装post请求参数
        if (null != paramMap && paramMap.size() > 0) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            // 通过map集成entrySet方法获取entity
            Set<Map.Entry<String, Object>> entrySet = paramMap.entrySet();
            // 循环遍历，获取迭代器
            Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> mapEntry = iterator.next();
                nvps.add(new BasicNameValuePair(mapEntry.getKey(), mapEntry.getValue().toString()));
            }

            // 为httpPost设置封装好的请求参数
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        try {
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
