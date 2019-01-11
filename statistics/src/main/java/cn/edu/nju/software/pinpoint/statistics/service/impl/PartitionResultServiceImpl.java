package cn.edu.nju.software.pinpoint.statistics.service.impl;

import cn.edu.nju.software.pinpoint.statistics.dao.PartitionResultMapper;
import cn.edu.nju.software.pinpoint.statistics.entity.PartitionResult;
import cn.edu.nju.software.pinpoint.statistics.entity.PartitionResultExample;
import cn.edu.nju.software.pinpoint.statistics.service.PartitionResultService;
import com.github.pagehelper.PageHelper;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
public class PartitionResultServiceImpl implements PartitionResultService {
    @Autowired
    private PartitionResultMapper partitionResultMapper;

    @Autowired
    private Sid sid;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PartitionResult savePartitionResult(PartitionResult partitionResult) {
        String id = sid.nextShort();
        partitionResult.setId(id);
        partitionResult.setCreatedat(new Date());
        partitionResult.setUpdatedat(new Date());
        partitionResult.setFlag(1);
        partitionResultMapper.insert(partitionResult);
        return partitionResult;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePartitionResult(PartitionResult partitionResult) {
        partitionResult.setUpdatedat(new Date());
        PartitionResultExample example = new PartitionResultExample();
        PartitionResultExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(partitionResult.getId()).andFlagEqualTo(1);
        partitionResultMapper.updateByExampleSelective(partitionResult, example);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deletePartitionResult(String partitionResultId) {
        PartitionResult partitionResult = new PartitionResult();
        partitionResult.setId(partitionResultId);
        partitionResult.setFlag(0);
        partitionResult.setUpdatedat(new Date());
        PartitionResultExample example = new PartitionResultExample();
        PartitionResultExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(partitionResultId).andFlagEqualTo(1);
        partitionResultMapper.updateByExampleSelective(partitionResult, example);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PartitionResult queryPartitionResultById(String partitionResultId) {
        PartitionResult partitionResult = new PartitionResult();
        PartitionResultExample example = new PartitionResultExample();
        PartitionResultExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(partitionResultId).andFlagEqualTo(1);
        List<PartitionResult> partitionResults = partitionResultMapper.selectByExample(example);
        if (partitionResults.size() > 0 && partitionResults != null)
            partitionResult = partitionResults.get(0);
        return partitionResult;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<PartitionResult> queryPartitionResultListPaged(String dynamicInfoId,String algorithmsId,int type,Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);

        PartitionResultExample example = new PartitionResultExample();
        PartitionResultExample.Criteria criteria = example.createCriteria();
        criteria.andFlagEqualTo(1).andDynamicanalysisinfoidEqualTo(dynamicInfoId).
                andAlgorithmsidEqualTo(algorithmsId).andTypeEqualTo(type);
        example.setOrderByClause("createdat");
        List<PartitionResult> partitionResultList = partitionResultMapper.selectByExample(example);
        return partitionResultList;
    }
}
