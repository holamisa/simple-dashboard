package com.example.simpledashboard.reply.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

    // select * from reply where post_id = ?? AND status = ?? order by id desc
    public List<ReplyEntity> findAllByPostIdAndStatusOrderByIdDesc(Long postId, String status);
}
