package com.example.simpledashboard.reply.controller;

import com.example.simpledashboard.reply.db.ReplyEntity;
import com.example.simpledashboard.reply.model.Reply;
import com.example.simpledashboard.reply.model.ReplyDTO;
import com.example.simpledashboard.reply.model.ReplyView;
import com.example.simpledashboard.reply.service.ReplyConverter;
import com.example.simpledashboard.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reply")
@RequiredArgsConstructor
public class ReplyApiController {

    private final ReplyService replyService;
    private final ReplyConverter replyConverter;

    @PostMapping("/add")
    public ReplyDTO create(
            @Valid
            @RequestBody
            Reply reply
    ){
        var saveEntity = replyService.create(reply);

        return replyConverter.toDto(saveEntity);
    }

    @GetMapping("/post/{postId}")
    public List<ReplyDTO> findAllByPostId(
            @PathVariable Long postId
    ){
        return replyService.findAllByPostId(postId)
                .stream()
                .map(replyConverter::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/delete")
    public void delete(
            @RequestBody ReplyView replyView
    ){
        replyService.delete(replyView);
    }
}
