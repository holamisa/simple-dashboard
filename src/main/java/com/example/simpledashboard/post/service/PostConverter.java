package com.example.simpledashboard.post.service;

import com.example.simpledashboard.post.db.PostEntity;
import com.example.simpledashboard.post.model.PostDTO;
import com.example.simpledashboard.reply.service.ReplyConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostConverter {

    private final ReplyConverter replyConverter;

    public PostDTO toDto(PostEntity postEntity){

        var replyList = Optional.ofNullable(postEntity.getReplyList())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(replyConverter::toDto)
                .collect(Collectors.toList());

        return PostDTO.builder()
                .id(postEntity.getId())
                .boardId(postEntity.getBoard().getId())
                .userName(postEntity.getUserName())
                .password(postEntity.getPassword())
                .email(postEntity.getEmail())
                .status("REGISTERED")
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .postedAt(postEntity.getPostedAt())
                .replyList(replyList)
                .build();
    }
}
