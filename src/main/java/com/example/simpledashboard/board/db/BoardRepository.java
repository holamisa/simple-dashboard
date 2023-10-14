package com.example.simpledashboard.board.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    // select * from board where id = ?? and status = ??
    public Optional<BoardEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, String status);

    // select * from board order by id
    public List<BoardEntity> findAllByStatusOrderById(String status);
}
