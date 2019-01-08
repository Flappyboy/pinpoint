package cn.edu.nju.software.pinpoint.statistics.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FileUtil {

    //文件上传工具类服务方法

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception{

        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    //写文件
    public void writeFile(List<String> arrs, String path) throws IOException {

        // 写入中文字符时解决中文乱码问题
        FileOutputStream fos = new FileOutputStream(new File(path));
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        BufferedWriter bw = new BufferedWriter(osw);

        for (String arr : arrs) {
            bw.write(arr + "\n");
        }

        // 注意关闭的先后顺序，先打开的后关闭，后打开的先关闭
        bw.close();
        osw.close();
        fos.close();
    }

    //遍历文件夹，找到所有.class文件
    public static void traverseFolder(String path, ArrayList<String> myfiles) {

        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                // System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        traverseFolder(file2.getAbsolutePath(), myfiles);
                    } else {

                        if (file2.getName().endsWith(".class")) {
                            myfiles.add(file2.getAbsolutePath());
                            System.out.println("文件:" + file2.getAbsolutePath());
                        }

                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }

//    一行一行都文本文件
    public static List<String> readFile(String path) throws IOException {
        // 使用一个字符串集合来存储文本中的路径 ，也可用String []数组
        List<String> list = new ArrayList<String>();
        FileInputStream fis = new FileInputStream(path);
        // 防止路径乱码 如果utf-8 乱码 改GBK eclipse里创建的txt 用UTF-8，在电脑上自己创建的txt 用GBK
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String line = "";
        while ((line = br.readLine()) != null) {
            // 如果 t x t文件里的路径 不包含---字符串 这里是对里面的内容进行一个筛选
            // if (line.lastIndexOf("---") < 0) {
            list.add(line);
            // }
        }
        br.close();
        isr.close();
        fis.close();
        return list;
    }
}
