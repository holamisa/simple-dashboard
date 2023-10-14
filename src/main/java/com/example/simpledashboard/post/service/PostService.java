package com.example.simpledashboard.post.service;

import com.example.simpledashboard.board.db.BoardRepository;
import com.example.simpledashboard.common.Api;
import com.example.simpledashboard.common.Pagination;
import com.example.simpledashboard.post.db.PostEntity;
import com.example.simpledashboard.post.db.PostRepository;
import com.example.simpledashboard.post.model.Post;
import com.example.simpledashboard.post.model.PostDTO;
import com.example.simpledashboard.post.model.PostView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final PostConverter postConverter;

    public Api<PostDTO> create(
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

        return Api.<PostDTO>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data(postConverter.toDto(saveEntity))
                .build();
    }

    // 1. 게시글 존재?
    // 2. 비밀번호 일치?
    // 3. 해당 댓글에 대한 답변 리스트 조회
    public Api<PostDTO> view(
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

        return Api.<PostDTO>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data(postConverter.toDto(entity))
                .build();
    }

    public Api<List<PostDTO>> all(
            Pageable pageable,
            Long boardId
    ){
        var list = postConverter.toDto(postRepository.findAllByBoardIdAndStatusOrderByPostedAtDesc(pageable, boardId,"REGISTERED"));

        var pagination = Pagination.builder()
                .page(list.getNumber())
                .size(list.getSize())
                .currentElements(list.getNumberOfElements())
                .totalElements(list.getTotalElements())
                .totalPage(list.getTotalPages())
                .build();

        return Api.<List<PostDTO>>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data(list.toList())
                .pagination(pagination)
                .build();
    }

    // 1. 게시글 존재?
    // 2. 비밀번호 일치?
    // 3. 등록 --> 미등록 변경
    public Api delete(
            PostView postView
    ){
        postRepository.findById(postView.getPostId())
                .map(x -> {
                    if(!x.getPassword().equals(postView.getPassword())){
                        throw new ValidationException("패스워드가 일치 하지 않습니다.");
                    }

                    x.setStatus("UNREGISTERED");
                    postRepository.save(x);
                    return x;
                })
                .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));

        return Api.builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
