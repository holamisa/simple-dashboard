package com.example.simpledashboard.post.controller;

import com.example.simpledashboard.post.db.PostEntity;
import com.example.simpledashboard.post.model.Post;
import com.example.simpledashboard.post.model.PostView;
import com.example.simpledashboard.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    @PostMapping("/add")
    public PostEntity create(
            @Valid
            @RequestBody
            Post post
    ){
        return postService.create(post);
    }

    @PostMapping("/view")
    public PostEntity view(
            @Valid
            @RequestBody
            PostView postView
    ){
        return postService.view(postView);
    }

    @GetMapping("/all/{boardId}")
    public List<PostEntity> list(
            @PathVariable long boardId
    ){
        return postService.all(boardId);
    }

    @PostMapping("/delete")
    public void delete(
            @Valid
            @RequestBody
            PostView postView
    ){
        postService.delete(postView);
    }
}
