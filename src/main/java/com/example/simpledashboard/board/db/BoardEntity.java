package com.example.simpledashboard.board.db;

import com.example.simpledashboard.post.db.PostEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity(name = "board")
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_name", nullable = false, length = 100)
    private String boardName;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    // 1:N 관계
    @OneToMany(
            mappedBy = "board"
    )
    @Builder.Default
    @Where(clause = "status = 'REGISTERED'") // where annotation으로 필터링 가능하다.
    @org.hibernate.annotations.OrderBy(clause = "id desc")
    private List<PostEntity> postList = List.of();

}
