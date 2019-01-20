package cn.edu.nju.software.pinpoint.statistics.service.impl;

import cn.edu.nju.software.pinpoint.statistics.dao.AlgorithmsMapper;
import cn.edu.nju.software.pinpoint.statistics.dao.AppMapper;
import cn.edu.nju.software.pinpoint.statistics.dao.PartitionInfoMapper;
import cn.edu.nju.software.pinpoint.statistics.entity.*;
import cn.edu.nju.software.pinpoint.statistics.service.PartitionService;
import com.github.pagehelper.PageHelper;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class PartitionServiceImpl implements PartitionService {
    @Autowired
    private PartitionInfoMapper partitionMapper;
    @Autowired
    private AppMapper appMapper;
    @Autowired
    private AlgorithmsMapper algorithmsMapper;
    @Autowired
    private Sid sid;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void addPartition(PartitionInfo partition) {
        String id = sid.nextShort();
        partition.setId(id);
        partition.setCreatedat(new Date());
        partition.setUpdatedat(new Date());
        partition.setFlag(1);
        partition.setStatus(0);
        System.out.println(partition);
        partitionMapper.insertSelective(partition);

    }

    @Override
    public void delPartition(String id) {
        PartitionInfo partition = new PartitionInfo();
        partition.setId(id);
        partition.setFlag(0);
        updatePartition(partition);
    }

    @Override
    public void updatePartition(PartitionInfo partition) {
        partition.setUpdatedat(new Date());
        partitionMapper.updateByPrimaryKeySelective(partition);
    }

    @Override
    public List<HashMap<String, Object>> findBycondition(Integer page, Integer pageSize, String appName, String desc, String algorithmsid, Integer type) {
        // 开始分页
        PageHelper.startPage(page, pageSize);

        PartitionInfoExample example = new PartitionInfoExample();
        PartitionInfoExample.Criteria criteria = example.createCriteria();
        criteria.andFlagEqualTo(1);
        if (appName != null && appName != "" && !appName.isEmpty()) {
            AppExample appexample = new AppExample();
            AppExample.Criteria appcriteria = appexample.createCriteria();
            appcriteria.andFlagEqualTo(1).andNameEqualTo(appName);
            List<App> apps = appMapper.selectByExample(appexample);
            List<String> appIds = new ArrayList<>();
            for (App app : apps) {
                appIds.add(app.getId());
            }
            criteria.andAppidIn(appIds);
        }
        if (desc != null && desc != "" && !desc.isEmpty()) {
            criteria.andDescEqualTo(desc);
        }
        if (algorithmsid != "" && algorithmsid != null && !algorithmsid.isEmpty())
            criteria.andAlgorithmsidEqualTo(algorithmsid);
        if (type != null)
            criteria.andTypeEqualTo(type);
        example.setOrderByClause("createdat");
        List<PartitionInfo> partitionList = partitionMapper.selectByExample(example);
        List<HashMap<String, Object>> results = new ArrayList<>();
        HashMap<String, Object> result = new HashMap<>();
        for (PartitionInfo partitionInfo : partitionList) {
            String appid = partitionInfo.getAppid();
            App app = appMapper.selectByPrimaryKey(appid);
            String algoid = partitionInfo.getAlgorithmsid();
            Algorithms a = algorithmsMapper.selectByPrimaryKey(algoid);
            result.put("id", partitionInfo.getId());
            if (app != null)
                result.put("appName", app.getName());
            result.put("dynamicanalysisinfoid", partitionInfo.getDynamicanalysisinfoid());
            if (a != null)
                result.put("algorithmsName", a.getName());
            result.put("desc", partitionInfo.getDesc());
            result.put("status", partitionInfo.getStatus());
            result.put("type", partitionInfo.getType());
            result.put("createdat", partitionInfo.getCreatedat());
            result.put("updatedat", partitionInfo.getUpdatedat());
            results.add(result);

        }
        return results;
    }

    @Override
    public int count(String appName, String desc, String algorithmsid, Integer type) {
        PartitionInfoExample example = new PartitionInfoExample();
        PartitionInfoExample.Criteria criteria = example.createCriteria();
        criteria.andFlagEqualTo(1);
        if (appName != null && appName != "" && !appName.isEmpty()) {
            AppExample appexample = new AppExample();
            AppExample.Criteria appcriteria = appexample.createCriteria();
            appcriteria.andFlagEqualTo(1).andNameEqualTo(appName);
            List<App> apps = appMapper.selectByExample(appexample);
            List<String> appIds = new ArrayList<>();
            for (App app : apps) {
                appIds.add(app.getId());
            }
            criteria.andAppidIn(appIds);
        }
        if (desc != null && desc != "" && !desc.isEmpty()) {
            criteria.andDescEqualTo(desc);
        }
        if (algorithmsid != "" && algorithmsid != null && !algorithmsid.isEmpty())
            criteria.andAlgorithmsidEqualTo(algorithmsid);
        if (type != null)
            criteria.andTypeEqualTo(type);
        int count = partitionMapper.countByExample(example);
        return count;
    }


}
