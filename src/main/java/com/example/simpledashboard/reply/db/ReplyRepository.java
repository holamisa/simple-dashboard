package com.example.simpledashboard.reply.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

    // select * from reply where post_id = ?? AND status = ?? order by id desc
    public Page<ReplyEntity> findAllByPostIdAndStatusOrderByIdDesc(Pageable pageable, Long postId, String status);
}
