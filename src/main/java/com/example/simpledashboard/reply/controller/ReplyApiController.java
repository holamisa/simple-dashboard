package com.example.simpledashboard.reply.controller;

import com.example.simpledashboard.common.Api;
import com.example.simpledashboard.reply.model.Reply;
import com.example.simpledashboard.reply.model.ReplyDTO;
import com.example.simpledashboard.reply.model.ReplyView;
import com.example.simpledashboard.reply.service.ReplyConverter;
import com.example.simpledashboard.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/reply")
@RequiredArgsConstructor
public class ReplyApiController {

    private final ReplyService replyService;
    private final ReplyConverter replyConverter;

    @PostMapping("/add")
    public Api<ReplyDTO> create(
            @Valid
            @RequestBody
            Reply reply
    ){
        return replyService.create(reply);
    }

    @GetMapping("/post/{postId}")
    public Api<List<ReplyDTO>> findAllByPostId(
            @PathVariable Long postId,
            @PageableDefault(page = 0, size = 10)
//            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ){
        return replyService.findAllByPostId(pageable, postId);
    }

    @PostMapping("/delete")
    public Api delete(
            @RequestBody ReplyView replyView
    ){
        return replyService.delete(replyView);
    }
}
