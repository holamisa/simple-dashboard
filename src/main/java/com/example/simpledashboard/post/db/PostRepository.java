package com.example.simpledashboard.post.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    // select * from post where id = ?? AND status = ?? order by id desc limit 1
    public Optional<PostEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, String status);

    // select * from post where board_id = ?? AND status = ?? order by posted_at desc
    public List<PostEntity> findAllByBoardIdAndStatusOrderByPostedAtDesc(Long boardId, String status);
}
