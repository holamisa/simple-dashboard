package com.example.simpledashboard.board.service;

import com.example.simpledashboard.board.db.BoardEntity;
import com.example.simpledashboard.board.db.BoardRepository;
import com.example.simpledashboard.board.model.Board;
import com.example.simpledashboard.board.model.BoardDTO;
import com.example.simpledashboard.common.Api;
import com.example.simpledashboard.common.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardConverter boardConverter;

    public Api<BoardDTO> create(
            Board board
    ){
        var entity = BoardEntity.builder()
                .boardName(board.getBoardName())
                .status("REGISTERED")
                .build();

        var saveEntity = boardRepository.save(entity);

        return Api.<BoardDTO>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data(boardConverter.toDto(saveEntity))
                .build();
    }

    public Api<BoardDTO> view(
            Long boardId
    ){
        var entity = boardRepository.findFirstByIdAndStatusOrderByIdDesc(boardId, "REGISTERED")
                .orElseThrow(() -> new RuntimeException("해당 게시판은 존재하지 않습니다."));

        return Api.<BoardDTO>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data(boardConverter.toDto(entity))
                .build();
    }

    public Api<List<BoardDTO>> findAllByStatusOrderById(
            Pageable pageable
    ){
        var list = boardConverter.toDto(boardRepository.findAllByStatusOrderById(pageable, "REGISTERED"));

        var pagination = Pagination.builder()
                .page(list.getNumber())
                .size(list.getSize())
                .currentElements(list.getNumberOfElements())
                .totalElements(list.getTotalElements())
                .totalPage(list.getTotalPages())
                .build();

        return Api.<List<BoardDTO>>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data(list.toList())
                .pagination(pagination)
                .build();
    }

    public Api delete(
            Long boardId
    ){
        boardRepository.findById(boardId)
                        .map(x -> {
                            x.setStatus("UNREGISTERED");
                            boardRepository.save(x);
                            return x;
                        })
                        .orElseThrow(() -> new RuntimeException("해당 게시판은 존재하지 않습니다."));

        return Api.builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
