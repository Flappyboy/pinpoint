package cn.edu.nju.software.pinpoint.statistics.entity.common.github;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Author {
    private String name;
    private String email;
    private String date;
}
