package com.example.simpledashboard.board.db;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity(name = "board")
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_name", nullable = false, length = 100)
    private String boardName;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

}
