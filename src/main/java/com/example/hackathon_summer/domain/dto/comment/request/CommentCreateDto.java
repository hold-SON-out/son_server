package com.example.hackathon_summer.domain.dto.comment.request;

import com.example.hackathon_summer.domain.entity.Comment;
import com.example.hackathon_summer.domain.entity.Post;
import com.example.hackathon_summer.domain.entity.User;
import lombok.Data;

@Data
public class CommentCreateDto {
    private Long postId;
    private String content;

    public Comment toEntity(Post post, User user) {
        return Comment.builder()
                .content(content)
                .post(post)
                .user(user)
                .build();
    }
}
