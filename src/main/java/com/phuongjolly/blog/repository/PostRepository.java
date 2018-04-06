package com.phuongjolly.blog.repository;

import com.phuongjolly.blog.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}