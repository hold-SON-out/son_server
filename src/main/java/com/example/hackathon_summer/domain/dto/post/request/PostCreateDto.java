package com.example.hackathon_summer.domain.dto.post.request;

import com.example.hackathon_summer.domain.entity.Post;
import com.example.hackathon_summer.domain.entity.User;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PostCreateDto {
    @NotNull
    private String title;

    @NotNull
    private String content;

    public Post toEntity(User user) {
        return Post.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }
}
