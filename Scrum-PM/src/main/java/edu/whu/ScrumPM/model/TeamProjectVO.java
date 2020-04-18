package edu.whu.ScrumPM.model;

import lombok.Data;
import java.util.List;

@Data
public class TeamProjectVO {
    private Long teamProjectId;
    private String teamProjectName;
    private List<UserVO> userVOs;
    private List<IterationVO> iterationVOs;
}
