package com.phuongjolly.blog.controllers;

import com.phuongjolly.blog.models.*;
import com.phuongjolly.blog.services.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.AccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
    public Post getPostById(@PathVariable("id") Long id) {
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
    public ResponseEntity addNewComment(@PathVariable("id") Long id,
                                        @RequestBody Comment comment, Principal principal) {
        User currentUser = userController.getCurrentUserLogin(principal);
        HttpStatus status = HttpStatus.FORBIDDEN;
        if(currentUser != null){
            comment.setUser(currentUser);
            comment.setDate(new Date());
            comment = postService.addNewComment(comment, id);
            if(comment != null) {
                status = HttpStatus.OK;
                return new ResponseEntity<>(comment, status);
            }  else
            {
                status = HttpStatus.BAD_REQUEST;
                return new ResponseEntity<>(new ErrorResponse(400, "Add comment failed"), status);
            }
        }
        return new ResponseEntity<>(new ErrorResponse(400, "Add comment failed"), status);
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

    @GetMapping("/tags/{name}")
    public List<Post> getPostsByTagName(@PathVariable("name") String name) {
        return postService.getPostsByTagName(name);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    public ResponseEntity deletePostById(@PathVariable("id") Long id) {
        HttpStatus status;
        try{
            postService.deletePostById(id);
            status = HttpStatus.OK;
            return new ResponseEntity<>(new String("OK"), status);
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(new ErrorResponse(400, "Delete failed"), status);
        }
    }
}
