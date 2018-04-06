package com.phuongjolly.blog.services;

import com.phuongjolly.blog.models.Post;

import java.util.Optional;

public interface PostService {
    Post addNewPost( Post post );
    Iterable<Post> getAllPosts();
    Optional<Post> getPostById( Long id);

}
