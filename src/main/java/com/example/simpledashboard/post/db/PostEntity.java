package com.example.simpledashboard.post.db;

import com.example.simpledashboard.board.db.BoardEntity;
import com.example.simpledashboard.reply.db.ReplyEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity(name = "post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private BoardEntity board; // 해당 변수는 시스템에서 자동으로 _id 추가해줌

    @Column(name = "user_name", nullable = false, length = 50)
    private String userName;

    @Column(name = "password", nullable = false, length = 20)
    private String password;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "posted_at", nullable = false)
    private LocalDateTime postedAt;

    @OneToMany(
            mappedBy = "post"
    )
    @Builder.Default
//    @Where(clause = "status = 'REGISTERED'") // where annotation으로 필터링 가능하다.
//    @org.hibernate.annotations.OrderBy(clause = "id desc")
    private List<ReplyEntity> replyList = List.of();
}