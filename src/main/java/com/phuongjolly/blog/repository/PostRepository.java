package com.phuongjolly.blog.repository;

import com.phuongjolly.blog.models.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findAllByTags_Name(String name);
}