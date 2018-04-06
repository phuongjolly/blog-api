package com.phuongjolly.blog.services;

import com.phuongjolly.blog.models.Comment;
import com.phuongjolly.blog.models.Post;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface PostService {
    Post addNewPost( Post post );
    Iterable<Post> getAllPosts();
    Optional<Post> getPostById( Long id);
    Comment addNewComment(Comment comment, Long postId);
    List<Comment> getCommentsByPostId(Long postId);
}
