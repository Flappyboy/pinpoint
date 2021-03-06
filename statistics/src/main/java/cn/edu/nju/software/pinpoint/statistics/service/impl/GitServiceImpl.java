package cn.edu.nju.software.pinpoint.statistics.service.impl;

import cn.edu.nju.software.pinpoint.statistics.dao.GitMapper;
import cn.edu.nju.software.pinpoint.statistics.entity.Git;
import cn.edu.nju.software.pinpoint.statistics.service.GitService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Slf4j
@Service
public class GitServiceImpl implements GitService {
    @Autowired
    GitMapper gitMapper;

    @Override
    public List<Git> queryGitByAppId(String appId) {
        Example example = new Example(Git.class);
        example.setForUpdate(true);
        example.createCriteria().andEqualTo("appId",appId);
        List<Git> gitList = gitMapper.selectByExample(example);
        return gitList;
    }

    @Override
    public List<Git> queryGit(Git git) {


        Example example = new Example(Git.class);
        if(StringUtils.isNoneBlank(git.getAppId()))
            example.createCriteria().andEqualTo("appId",git.getAppId());
        List<Git> gitList = gitMapper.selectByExample(example);

        return gitList;
    }
}
