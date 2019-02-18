package cn.edu.nju.software.pinpoint.statistics.service.impl;

import cn.edu.nju.software.pinpoint.statistics.dao.PartitionDetailMapper;
import cn.edu.nju.software.pinpoint.statistics.entity.*;
import cn.edu.nju.software.pinpoint.statistics.service.ClassNodeService;
import cn.edu.nju.software.pinpoint.statistics.service.MethodNodeService;
import cn.edu.nju.software.pinpoint.statistics.service.PartitionDetailService;
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
public class PartitionDetailServiceImpl implements PartitionDetailService {
    @Autowired
    private Sid sid;
    @Autowired
    private ClassNodeService classNodeService;
    @Autowired
    private MethodNodeService methodNodeService;
    @Autowired
    private PartitionDetailMapper partitionDetailMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PartitionDetail savePartitionDetail(PartitionDetail partitionDetail) {
        String id = sid.nextShort();
        partitionDetail.setId(id);
        partitionDetail.setCreatedat(new Date());
        partitionDetail.setUpdatedat(new Date());
        partitionDetail.setFlag(1);
        partitionDetailMapper.insert(partitionDetail);
        return partitionDetail;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePartitionDetail(PartitionDetail partitionDetail) {
        partitionDetail.setUpdatedat(new Date());
        PartitionDetailExample example = new PartitionDetailExample();
        PartitionDetailExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(partitionDetail.getId()).andFlagEqualTo(1);
        partitionDetailMapper.updateByExampleSelective(partitionDetail, example);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deletePartitionDetail(String partitionDetailId) {
        PartitionDetail partitionDetail = new PartitionDetail();
        partitionDetail.setId(partitionDetailId);
        partitionDetail.setFlag(0);
        partitionDetail.setUpdatedat(new Date());
        PartitionDetailExample example = new PartitionDetailExample();
        PartitionDetailExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(partitionDetailId).andFlagEqualTo(1);
        partitionDetailMapper.updateByExampleSelective(partitionDetail, example);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PartitionDetail queryPartitionDetailById(String partitionDetailId) {
        PartitionDetail partitionDetail = new PartitionDetail();
        PartitionDetailExample example = new PartitionDetailExample();
        PartitionDetailExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(partitionDetailId).andFlagEqualTo(1);
        List<PartitionDetail> partitionDetails = partitionDetailMapper.selectByExample(example);
        if (partitionDetails.size() > 0 && partitionDetails != null)
            partitionDetail = partitionDetails.get(0);
        return partitionDetail;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<HashMap<String, String>> queryPartitionDetailListPaged(String id, int type, Integer page, Integer pageSize) {
        List<HashMap<String, String>> nodes = new ArrayList<>();
        PageHelper.startPage(page, pageSize);
        PartitionDetailExample example = new PartitionDetailExample();
        PartitionDetailExample.Criteria criteria = example.createCriteria();
        criteria.andFlagEqualTo(1).andPatitionresultidEqualTo(id).andTypeEqualTo(type);
        example.setOrderByClause("createdat");
        List<PartitionDetail> mylist = partitionDetailMapper.selectByExample(example);
        for (PartitionDetail pd : mylist) {
            HashMap<String, String> nodemap = new HashMap<String, String>();
            if (type == 0) {
                ClassNode node = classNodeService.findById(pd.getNodeid());
                nodemap.put("nodeName", node.getName());
            }
            if (type == 1) {
                MethodNode node = methodNodeService.findById(pd.getNodeid());
                nodemap.put("nodeName", node.getName());
            }
            nodes.add(nodemap);
        }
        return nodes;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int countOfPartitionDetail(String id, int type) {
        PartitionDetailExample example = new PartitionDetailExample();
        PartitionDetailExample.Criteria criteria = example.createCriteria();
        criteria.andFlagEqualTo(1).andPatitionresultidEqualTo(id).andTypeEqualTo(type);
        return partitionDetailMapper.countByExample(example);
    }

    @Override
    public int countOfPartitionDetail(String id) {
        PartitionDetailExample example = new PartitionDetailExample();
        PartitionDetailExample.Criteria criteria = example.createCriteria();
        criteria.andFlagEqualTo(1).andPatitionresultidEqualTo(id);
        return partitionDetailMapper.countByExample(example);
    }

    @Override
    public List<Object> queryPartitionDetailByResultId(String partitionResultId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        PartitionDetailExample example = new PartitionDetailExample();
        PartitionDetailExample.Criteria criteria = example.createCriteria();
        criteria.andFlagEqualTo(1).andPatitionresultidEqualTo(partitionResultId);
        example.setOrderByClause("createdat");
        List<PartitionDetail> mylist = partitionDetailMapper.selectByExample(example);
        List<Object> nodes = new ArrayList();
        for (PartitionDetail pd : mylist) {
            Object node;
            int type = pd.getType();
            if (type == 0) {
                node = classNodeService.findById(pd.getNodeid());
            }else{
                node = methodNodeService.findById(pd.getNodeid());
            }
            nodes.add(node);
        }
        return nodes;
    }

    @Override
    public int countOfPartitionDetailByResultId(String id) {
        PartitionDetailExample example = new PartitionDetailExample();
        PartitionDetailExample.Criteria criteria = example.createCriteria();
        criteria.andFlagEqualTo(1).andPatitionresultidEqualTo(id);
        return partitionDetailMapper.countByExample(example);
    }
}
