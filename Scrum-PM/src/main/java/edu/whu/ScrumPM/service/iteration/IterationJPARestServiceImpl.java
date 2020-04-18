package edu.whu.ScrumPM.service.iteration;



import edu.whu.ScrumPM.dao.iteration.Iteration;
import edu.whu.ScrumPM.dao.iteration.IterationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class IterationJPARestServiceImpl implements IterationRestService {

    @Resource
    private IterationRepository iterationRepository;

    @Override
    public Iteration saveIteration(Iteration iteration) {
        iterationRepository.save(iteration);
        return iteration;
    }

    @Override
    public void deleteIteration(Long iterationId) {
        iterationRepository.deleteById(iterationId);
    }

    @Override
    public void updateIteration(Iteration iteration) {
        iterationRepository.save(iteration);
    }

    @Override
    public Iteration getIteration(Long iterationId) {
        return iterationRepository.findById(iterationId).get();
    }

    @Override
    public List<Iteration> getIterationByTeamProjectId(Long teamProjectId) {
        return iterationRepository.getIterationsByTeamProjectId(teamProjectId);
    }
}
