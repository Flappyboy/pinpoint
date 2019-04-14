package cn.edu.nju.software.algorithm.mstcluster;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClassNodeFromDB {
    public static int lastIndexLetter(String str, char ch, int lin) {
        char[] array = str.toCharArray();
        for (int i = array.length - 1; i > -1; i--) {
            if (array[i] == ch && --lin == 0) {
                return i;
            }
        }
        return -1;

    }

    public static String changeName(String str, char ch, int lin){
        int index = lastIndexLetter(str,'.',3);
        String defaultName = str.substring(index+1);
        return defaultName;
    }

    public static String getClassNameById(String id){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String className = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pinpoint","root","!123456");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pinpoint","root","12345678");
            String sql = "select name from class_node where id = '" + id + "'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            if(rs.next()){
                className = rs.getString(1);
            }
            rs.close();
            ps.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return changeName(className, '.', 3);
    }

}
