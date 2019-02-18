package cn.edu.nju.software.pinpoint.statistics.entity.common.git;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class FileInfo {
    private String fileName;
    private int count;
}
