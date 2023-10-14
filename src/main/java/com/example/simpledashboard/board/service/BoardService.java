package com.example.simpledashboard.board.service;

import com.example.simpledashboard.board.db.BoardEntity;
import com.example.simpledashboard.board.db.BoardRepository;
import com.example.simpledashboard.board.model.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public BoardEntity view(
            Long boardId
    ){
        return boardRepository.findFirstByIdAndStatusOrderByIdDesc(boardId, "REGISTERED")
                .orElseThrow(() -> new RuntimeException("해당 게시판은 존재하지 않습니다."));
    }

    public List<BoardEntity> all(){
        return boardRepository.findAllByStatusOrderById("REGISTERED");
    }

    public void delete(
            Long boardId
    ){
        boardRepository.findById(boardId)
                        .map(x -> {
                            x.setStatus("UNREGISTERED");
                            boardRepository.save(x);
                            return x;
                        })
                        .orElseThrow(() -> new RuntimeException("해당 게시판은 존재하지 않습니다."));
    }
}
