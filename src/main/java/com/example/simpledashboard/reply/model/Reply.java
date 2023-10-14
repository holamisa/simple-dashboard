package com.example.simpledashboard.reply.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Reply {

    @NotNull
    private Long postId = 1L;

    @NotBlank
    private String userName;

    @NotBlank
    @Size(min = 4)
    private String password;

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
