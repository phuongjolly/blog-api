package com.phuongjolly.blog.controllers;

import com.phuongjolly.blog.models.Post;
import com.phuongjolly.blog.services.PostService;
import org.springframework.web.bind.annotation.*;
import java.util.Iterator;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public Post addNewPost(@RequestBody Post post) {
        return postService.addNewPost(post);
    }

    @GetMapping
    public @ResponseBody Iterable<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Optional<Post> getPostById(@PathVariable("id") Long id) {
        return postService.getPostById(id);
    }

    @GetMapping("/latest")
    public Long getLatestPostIdCreated(){
        Iterable<Post> posts = getAllPosts();
        Long maxId = 0L;
        for (Iterator i = posts.iterator(); i.hasNext();) {
            Post post = (Post) i.next();
            Long id = post.getId();
            if(maxId < id) {
                maxId = id;
            }
        }

        return maxId;
    }

}
