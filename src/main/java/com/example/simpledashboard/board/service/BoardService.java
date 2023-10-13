package com.example.simpledashboard.board.service;

import com.example.simpledashboard.board.db.BoardEntity;
import com.example.simpledashboard.board.db.BoardRepository;
import com.example.simpledashboard.board.model.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardEntity create(
            Board board
    ){
        var entity = BoardEntity.builder()
                .boardName(board.getBoardName())
                .status("REGISTERED")
                .build();

        return boardRepository.save(entity);
    }
}
