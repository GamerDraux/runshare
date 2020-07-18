package com.akproject.runshare.models.data;

import com.akproject.runshare.models.Trail;
import org.apache.catalina.startup.ContextRuleSet;
import org.springframework.data.repository.CrudRepository;

public interface TrailRepository extends CrudRepository<Trail, Integer> {

    Trail findByName(String name);
}
