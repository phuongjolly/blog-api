package com.phuongjolly.blog.services;

import com.phuongjolly.blog.models.Post;
import com.phuongjolly.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostDataService implements PostService {
    private PostRepository postRepository;

    PostDataService(PostRepository postRepository) {
        this.postRepository = postRepository;
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
}
