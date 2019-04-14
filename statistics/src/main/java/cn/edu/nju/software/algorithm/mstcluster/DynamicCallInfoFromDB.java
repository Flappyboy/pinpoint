package cn.edu.nju.software.algorithm.mstcluster;

import cn.edu.nju.software.pinpoint.statistics.entity.DynamicCallInfo;
import cn.edu.nju.software.pinpoint.statistics.entity.PartitionInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DynamicCallInfoFromDB {
    public static List<DynamicCallInfo> getDynamicCallInfos(PartitionInfo partitionInfo){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<DynamicCallInfo> list = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pinpoint","root","!123456");
            String sql = "select * from dynamic_call_info where flag = 1 and dynamicAnalysisInfoid = '" +
                     partitionInfo.getDynamicanalysisinfoid()+"' and type = '"+partitionInfo.getType()+"'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                DynamicCallInfo info = new DynamicCallInfo();
                info.setId(rs.getString(1));
                info.setCaller(rs.getString(2));
                info.setCallee(rs.getString(3));
                info.setCount(rs.getInt(4));
                info.setDynamicanalysisinfoid(rs.getString(5));
                info.setCreatedat(rs.getDate(6));
                info.setUpdatedat(rs.getDate(7));
                info.setIsinclude(rs.getInt(8));
                info.setFlag(rs.getInt(9));
                info.setType(rs.getInt(10));
                info.setDesc(rs.getString(11));
                list.add(info);
            }
            rs.close();
            ps.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
