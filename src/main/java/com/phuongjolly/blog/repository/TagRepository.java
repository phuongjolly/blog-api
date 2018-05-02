package com.phuongjolly.blog.repository;

import com.phuongjolly.blog.models.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag, Long> {
    List<Tag> findByPosts_Id(Long postId);
    Tag findByNameAndPosts_Id(String tagName, Long postId);
    Tag findByName(String name);
}
