package com.example.simpledashboard.reply.controller;

import com.example.simpledashboard.reply.db.ReplyEntity;
import com.example.simpledashboard.reply.model.Reply;
import com.example.simpledashboard.reply.model.ReplyView;
import com.example.simpledashboard.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/reply")
@RequiredArgsConstructor
public class ReplyApiController {

    private final ReplyService replyService;

    @PostMapping("/add")
    public ReplyEntity create(
            @Valid
            @RequestBody
            Reply reply
    ){
        return replyService.create(reply);
    }

    @GetMapping("/post/{postId}")
    public List<ReplyEntity> findAllByPostId(
            @PathVariable Long postId
    ){
        return replyService.findAllByPostId(postId);
    }

    @PostMapping("/delete")
    public void delete(
            @RequestBody ReplyView replyView
    ){
        replyService.delete(replyView);
    }
}
