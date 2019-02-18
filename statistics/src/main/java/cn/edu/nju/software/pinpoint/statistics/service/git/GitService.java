package cn.edu.nju.software.pinpoint.statistics.service.git;

import cn.edu.nju.software.pinpoint.statistics.entity.common.git.GitCommitRetn;
import cn.edu.nju.software.pinpoint.statistics.entity.common.github.CommitDetail;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;
import java.util.List;

public interface GitService {
    public GitCommitRetn getLocalCommit(String path) throws IOException, GitAPIException;
    public GitCommitRetn getRepositoryCommits(String userName,String repository);
}
