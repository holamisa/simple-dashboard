package com.example.simpledashboard.board.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    // select * from board where id = ?? and status = ??
    public Optional<BoardEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, String status);

    // select * from board order by id
    public Page<BoardEntity> findAllByStatusOrderById(Pageable pageable, String status);
}
