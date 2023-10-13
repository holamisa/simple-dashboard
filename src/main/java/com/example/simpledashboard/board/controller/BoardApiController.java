package com.example.simpledashboard.board.controller;

import com.example.simpledashboard.board.db.BoardEntity;
import com.example.simpledashboard.board.model.Board;
import com.example.simpledashboard.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
}
