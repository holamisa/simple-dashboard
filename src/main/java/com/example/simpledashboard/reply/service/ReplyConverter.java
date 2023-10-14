package com.example.simpledashboard.reply.service;

import com.example.simpledashboard.reply.db.ReplyEntity;
import com.example.simpledashboard.reply.model.ReplyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReplyConverter {

    public ReplyDTO toDto(ReplyEntity replyEntity){

        return ReplyDTO.builder()
                .id(replyEntity.getId())
                .postId(replyEntity.getPost().getId())
                .userName(replyEntity.getUserName())
                .password(replyEntity.getPassword())
                .status("REGISTERED")
                .title(replyEntity.getTitle())
                .content(replyEntity.getContent())
                .repliedAt(replyEntity.getRepliedAt())
                .build();
    }
}
