package com.example.hackathon_summer.controller;

import com.example.hackathon_summer.domain.dto.post.data.PostDto;
import com.example.hackathon_summer.domain.dto.post.request.PostCreateDto;
import com.example.hackathon_summer.domain.entity.Post;
import com.example.hackathon_summer.domain.entity.User;
import com.example.hackathon_summer.domain.response.Response;
import com.example.hackathon_summer.domain.response.ResponseData;
import com.example.hackathon_summer.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PostMapping("")
    public Response createPost(@RequestBody @Valid PostCreateDto postCreateDto,
                               HttpServletRequest request) {
        User author = (User) request.getAttribute("user");
        if (author == null) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "토큰이 필요함");
        }
        postService.createPost(postCreateDto, author);

        Response response = new Response();

        response.setStatus(HttpStatus.OK);
        response.setMassage("저장 성공");

        return response;
    }

    @GetMapping("")
    public ResponseData<List<PostDto>> getPosts() {
        List<PostDto> data = postService.getPosts();

        ResponseData<List<PostDto>> responseData = new ResponseData<>();
        responseData.setStatus(HttpStatus.OK);
        responseData.setMassage("조회 성공");
        responseData.setData(data);

        return responseData;
    }

    @GetMapping("/search/title")
    public ResponseData<List<PostDto>> searchTitle(@RequestParam String q) {
        List<PostDto> data = postService.getPostsTitle(q);

        ResponseData<List<PostDto>> responseData = new ResponseData<>();
        responseData.setStatus(HttpStatus.OK);
        responseData.setMassage("조회 성공");
        responseData.setData(data);

        return responseData;
    }

    @GetMapping("/search/name")
    public ResponseData<List<PostDto>> searchName(@RequestParam String q) {
        List<PostDto> data = postService.getPostsUser(q);

        ResponseData<List<PostDto>> responseData = new ResponseData<>();
        responseData.setStatus(HttpStatus.OK);
        responseData.setMassage("조회 성공");
        responseData.setData(data);

        return responseData;
    }
}
