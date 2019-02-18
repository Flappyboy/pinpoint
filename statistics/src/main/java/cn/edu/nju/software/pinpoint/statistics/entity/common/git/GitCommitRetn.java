package cn.edu.nju.software.pinpoint.statistics.entity.common.git;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Setter
@Getter
@ToString
public class GitCommitRetn {
    private List<GitCommitInfo> gitCommitInfos;
    private List<FileInfo> fileInfos;
}
