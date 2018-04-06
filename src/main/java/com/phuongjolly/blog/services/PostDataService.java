package com.phuongjolly.blog.services;

import com.phuongjolly.blog.models.Comment;
import com.phuongjolly.blog.models.Post;
import com.phuongjolly.blog.repository.CommentRepository;
import com.phuongjolly.blog.repository.PostRepository;
import com.phuongjolly.blog.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostDataService implements PostService {
    private PostRepository postRepository;
    private CommentRepository commentRepository;

    PostDataService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Post addNewPost(Post post) {
        post.setLikeCount(0);
        post.setCommentCount(0);

        postRepository.save(post);

        return post;
    }

    @Override
    public Iterable<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Comment addNewComment(Comment comment, Long postId) {
        Post post = getPostById(postId).get();
        if ( post != null) {
            comment.setPost(post);
            return commentRepository.save(comment);
        }
        return null;
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        return comments;
    }

}
