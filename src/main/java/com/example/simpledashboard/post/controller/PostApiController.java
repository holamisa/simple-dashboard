package com.example.simpledashboard.post.controller;

import com.example.simpledashboard.common.Api;
import com.example.simpledashboard.post.model.Post;
import com.example.simpledashboard.post.model.PostDTO;
import com.example.simpledashboard.post.model.PostView;
import com.example.simpledashboard.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    @PostMapping("/add")
    public PostDTO create(
            @Valid
            @RequestBody
            Post post
    ){
        return postService.create(post);
    }

    @PostMapping("/view")
    public PostDTO view(
            @Valid
            @RequestBody
            PostView postView
    ){
        return postService.view(postView);
    }

    @GetMapping("/all/{boardId}")
    public Api<List<PostDTO>> list(
            @PathVariable long boardId,
            @PageableDefault(page = 0, size = 10)
//            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ){
        return postService.all(pageable, boardId);
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
