package edu.whu.ScrumPM.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class IterationVO {
    private Long iterationId;

    private List<TaskVO> taskVOs;

    private String iterationState;

    private String iterationName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date iterationBeginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date iterationEndTime;
}
