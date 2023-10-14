package com.example.simpledashboard.board.model;

import com.example.simpledashboard.post.db.PostEntity;
import com.example.simpledashboard.post.model.PostDTO;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BoardDTO {

    private Long id;

    private String boardName;

    private String status;

    private List<PostDTO> postList = List.of();
}
