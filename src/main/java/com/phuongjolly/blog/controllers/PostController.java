package com.phuongjolly.blog.controllers;

import com.phuongjolly.blog.models.Comment;
import com.phuongjolly.blog.models.Post;
import com.phuongjolly.blog.models.User;
import com.phuongjolly.blog.services.PostService;
import com.phuongjolly.blog.services.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/posts")
public class PostController {

    private PostService postService;
    private UserController userController;

    public PostController(PostService postService, UserController userController) {
        this.postService = postService;
        this.userController = userController;
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

    @PostMapping("/{id}/addNewComment")
    public Comment addNewComment(@PathVariable("id") Long id,
                              @RequestBody Comment comment,
                              HttpSession session) {
        User currentUser = userController.getCurrentUserLogin(session);
        if(currentUser != null){
            comment.setUserId(currentUser.getId());
            postService.addNewComment(comment, id);
            return comment;
        }
        return null;
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getCommentsByPostId(@PathVariable("id") Long id) {
        return postService.getCommentsByPostId(id);
    }

}
