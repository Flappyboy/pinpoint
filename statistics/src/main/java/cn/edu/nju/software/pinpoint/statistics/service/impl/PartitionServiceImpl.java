package cn.edu.nju.software.pinpoint.statistics.service.impl;

import cn.edu.nju.software.pinpoint.statistics.dao.PartitionMapper;
import cn.edu.nju.software.pinpoint.statistics.entity.Partition;
import cn.edu.nju.software.pinpoint.statistics.service.PartitionService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class PartitionServiceImpl implements PartitionService {
    @Autowired
    private PartitionMapper partitionMapper;
    @Autowired
    private Sid sid;
    @Override
    public void addPartition(Partition partition) {
        String id = sid.nextShort();
        partition.setId(id);
        partition.setCreatedat(new Date());
        partition.setUpdatedat(new Date());
        partition.setFlag(1);
        partition.setStatus(0);
        partitionMapper.insertSelective(partition);

    }
}
