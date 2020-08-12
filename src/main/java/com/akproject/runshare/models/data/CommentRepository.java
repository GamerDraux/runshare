package com.akproject.runshare.models.data;

import com.akproject.runshare.models.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {

    Comment findById(int id);

    List<Comment> findFirst10ByOrderByDateCreatedDescTimeCreatedDesc();


    List<Comment> findByTrail_IdOrderByDateCreatedDescTimeCreatedDesc(Integer trail);

    List<Comment> findByRunSession_IdOrderByDateCreatedDescTimeCreatedDesc(Integer runSession);

    List<Comment> findByRunners_IdOrderByDateCreatedDescTimeCreatedDesc(Integer Runner);
}
