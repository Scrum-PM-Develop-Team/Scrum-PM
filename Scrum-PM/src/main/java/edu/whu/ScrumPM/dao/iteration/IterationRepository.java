package edu.whu.ScrumPM.dao.iteration;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IterationRepository extends JpaRepository<Iteration,Long> {
    List<Iteration> getIterationsByTeamProjectId(Long teamProjectId);
}
