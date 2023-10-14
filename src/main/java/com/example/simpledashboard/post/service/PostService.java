package com.example.simpledashboard.post.service;

import com.example.simpledashboard.board.db.BoardRepository;
import com.example.simpledashboard.post.db.PostEntity;
import com.example.simpledashboard.post.db.PostRepository;
import com.example.simpledashboard.post.model.Post;
import com.example.simpledashboard.post.model.PostDTO;
import com.example.simpledashboard.post.model.PostView;
import com.example.simpledashboard.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final ReplyService replyService;
    private final PostConverter postConverter;

    public PostDTO create(
            Post post
    ){
        var boardEntity = boardRepository.findById(post.getBoardId());
        if(boardEntity.isEmpty()){
            throw new RuntimeException("게시판이 존재하지 않습니다.");
        }

        var entity = PostEntity.builder()
                .board(boardEntity.get())
                .userName(post.getUserName())
                .password(post.getPassword())
                .email(post.getEmail())
                .status("REGISTERED")
                .title(post.getTitle())
                .content(post.getContent())
                .postedAt(LocalDateTime.now())
                .build();

        var saveEntity = postRepository.save(entity);

        return postConverter.toDto(saveEntity);
    }
    // 1. 게시글 존재?
    // 2. 비밀번호 일치?
    // 3. 해당 댓글에 대한 답변 리스트 조회
    public PostDTO view(
            PostView postView
    ){
        var entity = postRepository.findFirstByIdAndStatusOrderByIdDesc(postView.getPostId(), "REGISTERED")
                .map(x -> {
                    if(!x.getPassword().equals(postView.getPassword())){
                        throw new RuntimeException("패스워드가 일치 하지 않습니다.");
                    }

                    return x;
                })
                .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));

        return postConverter.toDto(entity);
    }

    public List<PostDTO> all(
            Long boardId
    ){
        return postRepository.findAllByBoardIdAndStatusOrderByPostedAtDesc(boardId,"REGISTERED")
                .stream()
                .map(postConverter::toDto)
                .collect(Collectors.toList());
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
