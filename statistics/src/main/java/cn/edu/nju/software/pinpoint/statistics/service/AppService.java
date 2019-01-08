package cn.edu.nju.software.pinpoint.statistics.service;

import cn.edu.nju.software.pinpoint.statistics.entity.App;

import java.util.List;

public interface AppService {
    public App saveApp(App app);

    public void updateApp(App app);

    public void deleteApp(String appId);

    public App queryAppById(String appId);

    public List<App> queryUserListPaged(Integer page, Integer pageSize);

}
