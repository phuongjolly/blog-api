package com.phuongjolly.blog.services;

import com.phuongjolly.blog.models.Comment;
import com.phuongjolly.blog.models.Post;
import com.phuongjolly.blog.models.Tag;
import com.phuongjolly.blog.repository.CommentRepository;
import com.phuongjolly.blog.repository.PostRepository;
import com.phuongjolly.blog.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PostDataService implements PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostDataService.class);

    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private TagRepository tagRepository;

    PostDataService(PostRepository postRepository, CommentRepository commentRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public Post addNewPost(Post post) {
        post.setLikeCount(0);
        post.setCommentCount(0);
        post.setDisplay(true);
        postRepository.save(post);

        return post;
    }

    @Override
    public Iterable<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id).get();
    }

    @Override
    public Comment addNewComment(Comment comment, Long postId) {
        Post post = getPostById(postId);
        if ( post != null) {
            if(!comment.getContent().isEmpty()) {
                comment.setPost(post);
                return commentRepository.save(comment);
            }
        }
        return null;
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        return comments;
    }

    @Override
    public List<Tag> getTagsByPostId(Long postId) {
        return tagRepository.findByPosts_Id(postId);
    }

    @Override
    public List<Post> getPostsByTagName(String name) {
        List<Post> posts = postRepository.findAllByTags_Name(name);
        return posts;
    }

    @Override
    public void deletePostById(Long id) {
        Post post = getPostById(id);
        if(post != null) {
            postRepository.delete(post);
        }
    }

}
