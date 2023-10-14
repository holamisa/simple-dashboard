package com.example.simpledashboard.board.service;

import com.example.simpledashboard.board.db.BoardEntity;
import com.example.simpledashboard.board.db.BoardRepository;
import com.example.simpledashboard.board.model.Board;
import com.example.simpledashboard.board.model.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardConverter boardConverter;
    public BoardDTO create(
            Board board
    ){
        var entity = BoardEntity.builder()
                .boardName(board.getBoardName())
                .status("REGISTERED")
                .build();

        var saveEntity = boardRepository.save(entity);

        return boardConverter.toDto(saveEntity);
    }

    public BoardDTO view(
            Long boardId
    ){
        var entity = boardRepository.findFirstByIdAndStatusOrderByIdDesc(boardId, "REGISTERED")
                .orElseThrow(() -> new RuntimeException("해당 게시판은 존재하지 않습니다."));

        return boardConverter.toDto(entity);
    }

    public List<BoardDTO> findAllByStatusOrderById(){

        return boardRepository.findAllByStatusOrderById("REGISTERED").stream()
                .map(boardConverter::toDto).collect(Collectors.toList());
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
