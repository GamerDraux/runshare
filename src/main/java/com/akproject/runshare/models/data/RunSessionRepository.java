package com.akproject.runshare.models.data;

import com.akproject.runshare.models.RunSession;
import org.springframework.data.repository.CrudRepository;

public interface RunSessionRepository extends CrudRepository<RunSession, Integer> {

    RunSession findByName(String name);
}
