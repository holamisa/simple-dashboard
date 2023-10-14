package com.example.simpledashboard.board.service;

import com.example.simpledashboard.board.db.BoardEntity;
import com.example.simpledashboard.board.model.BoardDTO;
import com.example.simpledashboard.post.service.PostConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardConverter {

    private final PostConverter postConverter;

    public BoardDTO toDto(BoardEntity boardEntity){

        var postList = Optional.ofNullable(boardEntity.getPostList())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(postConverter::toDto)
                .collect(Collectors.toList());


        return BoardDTO.builder()
                .id(boardEntity.getId())
                .boardName(boardEntity.getBoardName())
                .status(boardEntity.getStatus())
                .postList(postList)
                .build();
    }
}
