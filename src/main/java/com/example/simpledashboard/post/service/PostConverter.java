package com.example.simpledashboard.post.service;

import com.example.simpledashboard.post.db.PostEntity;
import com.example.simpledashboard.post.model.PostDTO;
import com.example.simpledashboard.reply.service.ReplyConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostConverter {

    private final ReplyConverter replyConverter;

    public PostDTO toDto(PostEntity postEntity){
        return getPostDTO(postEntity);
    }

    public Page<PostDTO> toDto(Page<PostEntity> postEntityPage){
        return postEntityPage.map(this::getPostDTO);
    }

    private PostDTO getPostDTO(PostEntity x) {
        var replyList = Optional.ofNullable(x.getReplyList())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(replyConverter::toDto)
                .collect(Collectors.toList());

        return PostDTO.builder()
                .id(x.getId())
                .boardId(x.getBoard().getId())
                .userName(x.getUserName())
                .password(x.getPassword())
                .email(x.getEmail())
                .status("REGISTERED")
                .title(x.getTitle())
                .content(x.getContent())
                .postedAt(x.getPostedAt())
                .replyList(replyList)
                .build();
    }
}
