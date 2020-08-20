package com.akproject.runshare.models.data;

import com.akproject.runshare.models.Runner;
import com.akproject.runshare.models.Trail;
import com.akproject.runshare.models.TrailDifficultyRating;
import com.akproject.runshare.models.enums.TrailDifficulty;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrailDifficultyRatingRepository extends CrudRepository<TrailDifficultyRating, Integer> {

    TrailDifficultyRating findByRunner_IdAndTrail_Id(int runner, int trail);

    List<TrailDifficultyRating> findAllByTrail_Id(Integer trailId);

    List<TrailDifficultyRating> findAllByRunner_Id(Integer runnerId);

}
