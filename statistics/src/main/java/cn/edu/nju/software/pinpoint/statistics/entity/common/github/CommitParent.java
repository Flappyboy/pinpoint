package cn.edu.nju.software.pinpoint.statistics.entity.common.github;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CommitParent {
    private String sha;
    private String url;
    private String html_url;
}
