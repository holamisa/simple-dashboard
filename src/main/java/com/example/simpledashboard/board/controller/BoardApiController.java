package com.example.simpledashboard.board.controller;

import com.example.simpledashboard.board.model.Board;
import com.example.simpledashboard.board.model.BoardDTO;
import com.example.simpledashboard.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public BoardDTO create(
            @Valid
            @RequestBody
            Board board
    ){
        return boardService.create(board);
    }

    @GetMapping("/view/{boardId}")
    public BoardDTO view(
            @PathVariable Long boardId
    ){
        var entity = boardService.view(boardId);
        log.info("BoardEntity : {}", entity);
        return entity;
    }

    @GetMapping("/list")
    public List<BoardDTO> list(){

        return boardService.findAllByStatusOrderById();
    }

    @DeleteMapping("/delete/{boardId}")
    public void delete(
            @PathVariable Long boardId
    ){
        boardService.delete(boardId);
    }
}
