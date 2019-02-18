package cn.edu.nju.software.pinpoint.statistics.controller;

import cn.edu.nju.software.pinpoint.statistics.entity.common.JSONResult;
import cn.edu.nju.software.pinpoint.statistics.entity.common.git.GitCommitRetn;
import cn.edu.nju.software.pinpoint.statistics.service.git.GitService;
import io.swagger.annotations.Api;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@CrossOrigin
@RestController
@Api(value = "git相关接口")
@RequestMapping(value = "/api")
public class GitController {
    @Autowired
    private GitService gitService;

    @RequestMapping(value = "/git", method = RequestMethod.GET)
    public JSONResult queryAppListPaged(Integer flag, String path) throws IOException, GitAPIException {
        GitCommitRetn gitCommitRetn = new GitCommitRetn();
//        https://github.com/WCXwcx/PetStore.git
        if (flag == null || flag == 1) {
//            gitCommitRetn = gitService.getLocalCommit("/Users/yaya/Documents/mycode/intelliJIdea/journey/");
            gitCommitRetn = gitService.getLocalCommit(path);
        }
        if (flag == 2) {
//            String username=;
//            String repository=;
//            gitCommitRetn = gitService.getRepositoryCommits("yzgqy","journey");
            String[] paths = path.substring(19).split("/");
            String username = paths[0];
            int index = paths[1].lastIndexOf(".");
            String repository = paths[1].substring(0, index);
            System.out.println(username);
            System.out.println(repository);
            gitCommitRetn = gitService.getRepositoryCommits(username, repository);
        }

        return JSONResult.ok(gitCommitRetn);
    }

    public static void main(String[] args) {
        String a = "https://github.com/WCXwcx/PetStore.git";
        String[] a1 = a.substring(19).split("/");
        int index = a1[1].lastIndexOf(".");
        System.out.println(a1[1].substring(0, index));
    }
}
