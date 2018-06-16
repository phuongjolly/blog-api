package com.phuongjolly.blog.services;

import com.phuongjolly.blog.models.Comment;
import com.phuongjolly.blog.models.Post;
import com.phuongjolly.blog.models.Tag;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    Post addNewPost( Post post );
    Iterable<Post> getAllPosts();
    Post getPostById( Long id);
    Comment addNewComment(Comment comment, Long postId);
    List<Comment> getCommentsByPostId(Long postId);
    List<Tag> getTagsByPostId(Long postId);
    List<Post> getPostsByTagName(String name);
    void deletePostById(Long id);
    Page<Post> getPostsByPage(int page, int size);
    long getPostCount();
}
