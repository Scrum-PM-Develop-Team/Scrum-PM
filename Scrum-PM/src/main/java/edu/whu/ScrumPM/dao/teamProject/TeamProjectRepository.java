package edu.whu.ScrumPM.dao.teamProject;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface TeamProjectRepository extends JpaRepository<TeamProject,Long> {

    Optional<TeamProject> findByTeamProjectId(Long teamProjectId);
    List<TeamProject> findAll();
}