package cn.edu.nju.software.algorithm.mstcluster;

import cn.edu.nju.software.git.entity.GitCommitFileEdge;
import cn.edu.nju.software.pinpoint.statistics.entity.PartitionInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PartitionInfoFromDB {
    public static PartitionInfo getPartitionInfoById(String id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        PartitionInfo partitionInfo = new PartitionInfo();
        try{
            Class.forName("com.mysql.jdbc.Driver");
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pinpoint","root","!123456");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pinpoint","root","12345678");
            String sql = "select * from partition_info where id = '" + id + "'";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            if (resultSet.next()){
                partitionInfo.setId(resultSet.getString(1));
                partitionInfo.setAppid(resultSet.getString(2));
                partitionInfo.setDynamicanalysisinfoid(resultSet.getString(3));
                partitionInfo.setAlgorithmsid(resultSet.getString(4));
                partitionInfo.setDesc(resultSet.getString(5));
                partitionInfo.setStatus(resultSet.getInt(6));
                partitionInfo.setFlag(resultSet.getInt(7));
                partitionInfo.setType(resultSet.getInt(8));
                partitionInfo.setCreatedat(resultSet.getDate(9));
                partitionInfo.setUpdatedat(resultSet.getDate(10));
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return partitionInfo;
    }

 }
