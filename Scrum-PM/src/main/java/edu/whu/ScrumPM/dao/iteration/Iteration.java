package edu.whu.ScrumPM.dao.iteration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
@Table(name="iteration")
public class Iteration {
    @Id
    @GeneratedValue
    private Long iterationId;
    private Long teamProjectId;
    private String iterationState;
    private String iterationName;
    private Date iterationBeginTime;
    private Date iterationEndTime;
}
