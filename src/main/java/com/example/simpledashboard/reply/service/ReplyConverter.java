package com.example.simpledashboard.reply.service;

import com.example.simpledashboard.reply.db.ReplyEntity;
import com.example.simpledashboard.reply.model.ReplyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyConverter {

    public ReplyDTO toDto(ReplyEntity replyEntity){

        return getReplyDTO(replyEntity);
    }

    public Page<ReplyDTO> toDto(Page<ReplyEntity> replyEntityPage){
        return replyEntityPage.map(this::getReplyDTO);
    }

    private ReplyDTO getReplyDTO(ReplyEntity x) {

        return ReplyDTO.builder()
                .id(x.getId())
                .postId(x.getPost().getId())
                .userName(x.getUserName())
                .password(x.getPassword())
                .status(x.getStatus())
                .title(x.getTitle())
                .content(x.getContent())
                .repliedAt(x.getRepliedAt())
                .build();
    }
}
