package com.akproject.runshare.models.data;

import com.akproject.runshare.models.Runner;
import net.bytebuddy.TypeCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RunnerRepository extends CrudRepository<Runner, Integer> {

    Runner findByCallsign(String callsign);


    List<Runner> findAllByOrderByCallsignAsc();
    List<Runner> findAllByOrderByCallsignDesc();

    List<Runner> findAllByOrderByFirstNameAsc();
    List<Runner> findAllByOrderByFirstNameDesc();

    List<Runner> findAllByOrderByLastNameAsc();
    List<Runner> findAllByOrderByLastNameDesc();

    List<Runner> findAllByOrderByRunnerLevelAsc();
    List<Runner> findAllByOrderByRunnerLevelDesc();

    List<Runner> findAllByOrderByAgeAsc();
    List<Runner> findAllByOrderByAgeDesc();
}
