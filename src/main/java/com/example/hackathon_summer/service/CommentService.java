package com.example.hackathon_summer.service;

import com.example.hackathon_summer.domain.dto.comment.request.CommentCreateDto;
import com.example.hackathon_summer.domain.entity.Comment;
import com.example.hackathon_summer.domain.entity.Post;
import com.example.hackathon_summer.domain.entity.User;
import com.example.hackathon_summer.domain.repository.CommentRepo;
import com.example.hackathon_summer.domain.repository.PostRepo;
import com.example.hackathon_summer.domain.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepo commentRepository;
    private final UserRepo userRepo;
    private final PostRepo postRepo;

    @Transactional
    public void createComment(CommentCreateDto commentCreateDto, User author) {
        try {
            Post post = postRepo.findById(commentCreateDto.getPostId()).orElseThrow(
                    () -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "해당 post 가 없습니다.")
            );
            Comment comment = commentCreateDto.toEntity(post, author);

            commentRepository.save(comment);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");
        }
    }
}
