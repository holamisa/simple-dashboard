package com.example.simpledashboard.board.service;

import com.example.simpledashboard.board.db.BoardEntity;
import com.example.simpledashboard.board.model.BoardDTO;
import com.example.simpledashboard.post.service.PostConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardConverter {

    private final PostConverter postConverter;

    public BoardDTO toDto(BoardEntity boardEntity){
        return getBoardDTO(boardEntity);
    }

    public Page<BoardDTO> toDto(Page<BoardEntity> boardEntityPage){
        return boardEntityPage.map(this::getBoardDTO);
    }

    private BoardDTO getBoardDTO(BoardEntity x) {
        var postList = Optional.ofNullable(x.getPostList())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(postConverter::toDto)
                .collect(Collectors.toList());

        return BoardDTO.builder()
                .id(x.getId())
                .boardName(x.getBoardName())
                .status(x.getStatus())
                .postList(postList)
                .build();
    }
}
