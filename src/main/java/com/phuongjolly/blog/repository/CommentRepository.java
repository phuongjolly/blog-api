package com.phuongjolly.blog.repository;

import com.phuongjolly.blog.models.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long>{
}
