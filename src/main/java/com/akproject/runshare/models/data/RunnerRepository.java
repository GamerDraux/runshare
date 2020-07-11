package com.akproject.runshare.models.data;

import com.akproject.runshare.models.Runner;
import org.springframework.data.repository.CrudRepository;

public interface RunnerRepository extends CrudRepository<Runner, Integer> {

    Runner findByCallsign(String callsign);
}
