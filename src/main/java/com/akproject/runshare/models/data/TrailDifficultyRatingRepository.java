package com.akproject.runshare.models.data;

import com.akproject.runshare.models.Runner;
import com.akproject.runshare.models.Trail;
import com.akproject.runshare.models.TrailDifficultyRating;
import com.akproject.runshare.models.enums.TrailDifficulty;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrailDifficultyRatingRepository extends CrudRepository<TrailDifficultyRating, Integer> {

    TrailDifficultyRating findByRunnerAndTrail(Runner runner, Trail trail);

    List<TrailDifficultyRating> findAllByTrailId(Integer trailId);

    List<TrailDifficultyRating> findAllByRunner(Runner runner);

}
