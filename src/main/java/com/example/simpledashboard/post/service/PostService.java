package com.example.simpledashboard.post.service;

import com.example.simpledashboard.post.db.PostEntity;
import com.example.simpledashboard.post.db.PostRepository;
import com.example.simpledashboard.post.model.Post;
import com.example.simpledashboard.post.model.PostView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostEntity create(
            Post post
    ){
        var entity = PostEntity.builder()
                .boardId(1L) // --> 임시로 고정
                .userName(post.getUserName())
                .password(post.getPassword())
                .email(post.getEmail())
                .status("REGISTERED")
                .title(post.getTitle())
                .content(post.getContent())
                .postedAt(LocalDateTime.now())
                .build();

        return postRepository.save(entity);
    }
    // 1. 게시글 존재?
    // 2. 비밀번호 일치?
    public PostEntity view(
            PostView postView
    ){
        return postRepository.findFirstByIdAndStatusOrderByIdDesc(postView.getPostId(), "REGISTERED")
                .map(x -> {
                    if(!x.getPassword().equals(postView.getPassword())){
                        throw new RuntimeException("패스워드가 일치 하지 않습니다.");
                    }
                    return x;
                })
                .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));
    }

    public List<PostEntity> all(
            Long boardId
    ){
        return postRepository.findAllByBoardIdAndStatusOrderByPostedAtDesc(boardId,"REGISTERED");
    }

    // 1. 게시글 존재?
    // 2. 비밀번호 일치?
    // 3. 등록 --> 미등록 변경
    public void delete(
            PostView postView
    ){
        postRepository.findById(postView.getPostId())
                .map(x -> {
                    if(!x.getPassword().equals(postView.getPassword())){
                        throw new RuntimeException("패스워드가 일치 하지 않습니다.");
                    }

                    x.setStatus("UNREGISTERED");
                    postRepository.save(x);
                    return x;
                })
                .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));
    }
}
