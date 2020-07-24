package com.akproject.runshare.models.data;

import com.akproject.runshare.models.Trail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TrailRepository extends CrudRepository<Trail, Integer> {

    Trail findByName(String name);

    List<Trail> findAllByOrderByNameAsc ();
    List<Trail> findAllByOrderByNameDesc ();

    List<Trail> findAllByOrderByAddressAsc();
    List<Trail> findAllByOrderByAddressDesc();

    List<Trail> findAllByOrderByMilesAsc ();
    List<Trail> findAllByOrderByMilesDesc ();
}
