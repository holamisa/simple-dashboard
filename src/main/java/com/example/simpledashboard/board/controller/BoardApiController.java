package com.example.simpledashboard.board.controller;

import com.example.simpledashboard.board.model.Board;
import com.example.simpledashboard.board.model.BoardDTO;
import com.example.simpledashboard.board.service.BoardService;
import com.example.simpledashboard.common.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping("/add")
    public Api<BoardDTO> create(
            @Valid
            @RequestBody
            Board board
    ){
        return boardService.create(board);
    }

    @GetMapping("/view/{boardId}")
    public Api<BoardDTO> view(
            @PathVariable Long boardId
    ){
        var entity = boardService.view(boardId);
        log.info("BoardEntity : {}", entity);
        return entity;
    }

    @GetMapping("/list")
    public Api<List<BoardDTO>> list(
            @PageableDefault(page = 0, size = 10)
//            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ){

        return boardService.findAllByStatusOrderById(pageable);
    }

    @DeleteMapping("/delete/{boardId}")
    public Api delete(
            @PathVariable Long boardId
    ){
        return boardService.delete(boardId);
    }
}
