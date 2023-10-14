package com.example.simpledashboard.board.controller;

import com.example.simpledashboard.board.db.BoardEntity;
import com.example.simpledashboard.board.model.Board;
import com.example.simpledashboard.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping("/add")
    public BoardEntity create(
            @Valid
            @RequestBody
            Board board
    ){
        return boardService.create(board);
    }

    @GetMapping("/view/{boardId}")
    public BoardEntity view(
            @PathVariable Long boardId
    ){
        return boardService.view(boardId);
    }

    @GetMapping("/list")
    public List<BoardEntity> list(){
        return boardService.all();
    }

    @DeleteMapping("/delete/{boardId}")
    public void delete(
            @PathVariable Long boardId
    ){
        boardService.delete(boardId);
    }
}
