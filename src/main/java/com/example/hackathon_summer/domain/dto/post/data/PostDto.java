package com.example.hackathon_summer.domain.dto.post.data;

import com.example.hackathon_summer.domain.dto.comment.data.CommentDto;
import com.example.hackathon_summer.domain.dto.user.data.UserDto;
import com.example.hackathon_summer.domain.entity.Post;
import lombok.Data;

import java.util.List;

@Data
public class PostDto {
    private Long idx;
    private String title;
    private String content;
    private UserDto user;
    private List<CommentDto> comments;

    public PostDto(Post post, UserDto user, List<CommentDto> list) {
        this.idx = post.getIdx();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.user = user;
        this.comments = list;
    }
}
