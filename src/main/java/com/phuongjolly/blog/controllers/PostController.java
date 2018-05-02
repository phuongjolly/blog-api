package com.phuongjolly.blog.controllers;

import com.phuongjolly.blog.models.Comment;
import com.phuongjolly.blog.models.Post;
import com.phuongjolly.blog.models.Tag;
import com.phuongjolly.blog.models.User;
import com.phuongjolly.blog.services.PostService;
import com.phuongjolly.blog.services.UserService;
import com.sun.tools.javac.tree.JCTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/posts")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

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
                                 @RequestBody Comment comment, Principal principal) {
        User currentUser = userController.getCurrentUserLogin(principal);
        if(currentUser != null){
            comment.setUser(currentUser);
            comment.setDate(new Date());
            postService.addNewComment(comment, id);
            return comment;
        }
        return null;
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getCommentsByPostId(@PathVariable("id") Long id) {
        return postService.getCommentsByPostId(id);
    }

    @GetMapping("/{id}/tags")
    public List<Tag> getTagsByPostId(@PathVariable("id") Long id) {
        List<Tag> tags = postService.getTagsByPostId(id);
        return tags;
    }
}
