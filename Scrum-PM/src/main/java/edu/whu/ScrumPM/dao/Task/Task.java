package edu.whu.ScrumPM.dao.Task;

import edu.whu.ScrumPM.model.UserVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="task")
public class Task {
    @Id
    @GeneratedValue
    private Long taskId;
    private Long iterationId;
    private String taskName;
    private String taskRemark;
    private String taskState;
    private Date taskEndTime;
    private Date taskBeginTime;
    private Date endTime;
    private String taskPriority;
    private Long userId;
}
