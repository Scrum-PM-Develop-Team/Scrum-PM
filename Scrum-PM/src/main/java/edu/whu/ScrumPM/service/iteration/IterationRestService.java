package edu.whu.ScrumPM.service.iteration;


import edu.whu.ScrumPM.dao.iteration.Iteration;

import java.util.List;

public interface IterationRestService {

     Iteration saveIteration(Iteration iteration);

     void deleteIteration(Long iterationId);

     void updateIteration(Iteration iteration);

     Iteration getIteration(Long iterationId);

     List<Iteration> getIterationByTeamProjectId(Long teamProjectId);
}