package cn.edu.nju.software.pinpoint.statistics.service.impl;

import cn.edu.nju.software.pinpoint.statistics.dao.AlgorithmsMapper;
import cn.edu.nju.software.pinpoint.statistics.dao.AlgorithmsParamMapper;
import cn.edu.nju.software.pinpoint.statistics.entity.Algorithms;
import cn.edu.nju.software.pinpoint.statistics.entity.AlgorithmsExample;
import cn.edu.nju.software.pinpoint.statistics.entity.AlgorithmsParam;
import cn.edu.nju.software.pinpoint.statistics.entity.AlgorithmsParamExample;
import cn.edu.nju.software.pinpoint.statistics.service.AlgorithmsService;
import com.github.pagehelper.PageHelper;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class AlgorithmsServiceImpl implements AlgorithmsService {

    @Autowired
    private AlgorithmsMapper algorithmsMapper;
    @Autowired
    private AlgorithmsParamMapper algorithmsParamMapper;
    @Autowired
    private Sid sid;

    @Override
    public void saveApp(Algorithms algorithms, List<AlgorithmsParam> algorithmsParams) {
        String id = sid.nextShort();
        algorithms.setId(id);
        algorithms.setCreatedat(new Date());
        algorithms.setUpdatedat(new Date());
        algorithms.setFlag(1);
        algorithmsMapper.insert(algorithms);
        for(AlgorithmsParam param :algorithmsParams){
            String paramId = sid.nextShort();
            param.setId(paramId);
            param.setCreatedat(new Date());
            param.setUpdatedat(new Date());
            param.setFlag(1);
            param.setAlgorithmsid(id);
            algorithmsParamMapper.insert(param);
        }
    }

    @Override
    public HashMap<String, Object> queryAlgorithmsById(String id) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        Algorithms algorithms = new Algorithms();
        AlgorithmsExample example = new AlgorithmsExample();
        AlgorithmsExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id).andFlagEqualTo(1);
        List<Algorithms> algorithmses = algorithmsMapper.selectByExample(example);
        if (algorithmses.size() > 0 && algorithmses != null)
            algorithms = algorithmses.get(0);

        AlgorithmsParamExample paramExample = new AlgorithmsParamExample();
        AlgorithmsParamExample.Criteria paramCriteria = paramExample.createCriteria();
        paramCriteria.andAlgorithmsidEqualTo(id).andFlagEqualTo(1);
        List<AlgorithmsParam> algorithmsParams = algorithmsParamMapper.selectByExample(paramExample);
        result.put("algorithms",algorithms);
        result.put("params",algorithmsParams);
        return result;
    }

    @Override
    public List<Algorithms> queryAlgorithmsListPaged(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        AlgorithmsExample example = new AlgorithmsExample();
        AlgorithmsExample.Criteria criteria = example.createCriteria();
        criteria.andFlagEqualTo(1);
        List<Algorithms> algorithmses = algorithmsMapper.selectByExample(example);
        return algorithmses;
    }
}
