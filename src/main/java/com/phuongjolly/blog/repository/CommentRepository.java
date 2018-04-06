package com.phuongjolly.blog.repository;

import com.phuongjolly.blog.models.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment, Long>{
    List<Comment> findAllByPostId(Long postId);
}
