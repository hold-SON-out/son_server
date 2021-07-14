package com.example.hackathon_summer.domain.dto.comment.data;

import com.example.hackathon_summer.domain.entity.Comment;
import lombok.Data;

@Data
public class CommentDto {
    private Long idx;
    private String content;

    public CommentDto(Comment comment) {
        this.idx = comment.getIdx();
        this.content = comment.getContent();
    }
}
