package com.akproject.runshare.models.data;

import com.akproject.runshare.models.RunSession;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RunSessionRepository extends CrudRepository<RunSession, Integer> {

    RunSession findByName(String name);

    List<RunSession> findAllByRunnerId(Integer runnerId);
}
