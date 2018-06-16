package com.phuongjolly.blog.repository;

import com.phuongjolly.blog.models.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
    List<Post> findAllByTags_Name(String name);
}