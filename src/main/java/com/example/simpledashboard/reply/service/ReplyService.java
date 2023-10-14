package com.example.simpledashboard.reply.service;

import com.example.simpledashboard.common.Api;
import com.example.simpledashboard.common.Pagination;
import com.example.simpledashboard.post.db.PostRepository;
import com.example.simpledashboard.reply.db.ReplyEntity;
import com.example.simpledashboard.reply.db.ReplyRepository;
import com.example.simpledashboard.reply.model.Reply;
import com.example.simpledashboard.reply.model.ReplyDTO;
import com.example.simpledashboard.reply.model.ReplyView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final ReplyConverter replyConverter;

    public Api<ReplyDTO> create(
            Reply reply
    ){
        var postEntity = postRepository.findById(reply.getPostId());
        if(postEntity.isEmpty()){
            throw new RuntimeException("게시물이 존재하지 않습니다.");
        }

        var entity = ReplyEntity.builder()
                .post(postEntity.get())
                .userName(reply.getUserName())
                .password(reply.getPassword())
                .status("REGISTERED")
                .title(reply.getTitle())
                .content(reply.getContent())
                .repliedAt(LocalDateTime.now())
                .build();

        var saveEntity = replyRepository.save(entity);

        return Api.<ReplyDTO>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data(replyConverter.toDto(saveEntity))
                .build();
    }

    public Api<List<ReplyDTO>> findAllByPostId(
            Pageable pageable,
            Long postId
    ){
        var list = replyConverter.toDto(replyRepository.findAllByPostIdAndStatusOrderByIdDesc(pageable, postId, "REGISTERED"));

        var pagination = Pagination.builder()
                .page(list.getNumber())
                .size(list.getSize())
                .currentElements(list.getNumberOfElements())
                .totalElements(list.getTotalElements())
                .totalPage(list.getTotalPages())
                .build();

        return Api.<List<ReplyDTO>>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data(list.toList())
                .pagination(pagination)
                .build();
    }

    public Api delete(
            ReplyView replyView
    ){
        replyRepository.findById(replyView.getReplyId())
                .map(x -> {
                    if(!x.getPassword().equals(replyView.getPassword())){
                        throw new RuntimeException("패스워드가 일치 하지 않습니다.");
                    }

                    x.setStatus("UNREGISTERED");
                    replyRepository.save(x);
                    return x;
                })
                .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));

        return Api.builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
