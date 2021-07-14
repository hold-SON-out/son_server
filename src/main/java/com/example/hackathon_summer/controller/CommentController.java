package com.example.hackathon_summer.controller;

import com.example.hackathon_summer.domain.dto.comment.request.CommentCreateDto;
import com.example.hackathon_summer.domain.entity.User;
import com.example.hackathon_summer.domain.response.Response;
import com.example.hackathon_summer.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("")
    public Response createComment(@RequestBody @Valid CommentCreateDto commentCreateDto,
                                  HttpServletRequest request) {

        User author = (User) request.getAttribute("user");
        if (author == null) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "토큰이 필요함");
        }

        commentService.createComment(commentCreateDto, author);

        Response response = new Response();
        response.setStatus(HttpStatus.OK);
        response.setMassage("저장 성공");

        return response;
    }
}
