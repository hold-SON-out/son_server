package com.example.hackathon_summer.service;

import com.example.hackathon_summer.domain.dto.comment.data.CommentDto;
import com.example.hackathon_summer.domain.dto.post.data.PostDto;
import com.example.hackathon_summer.domain.dto.post.request.PostCreateDto;
import com.example.hackathon_summer.domain.dto.user.data.UserDto;
import com.example.hackathon_summer.domain.entity.Comment;
import com.example.hackathon_summer.domain.entity.Post;
import com.example.hackathon_summer.domain.entity.User;
import com.example.hackathon_summer.domain.repository.PostRepo;
import com.example.hackathon_summer.domain.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepo postRepository;
    private final UserRepo userRepo;

    @Transactional
    public void createPost(PostCreateDto postCreateDto, User author) {
        try {

            Post post = postCreateDto.toEntity(author);

            postRepository.save(post);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");
        }
    }

    @Transactional(readOnly = true)
    public List<PostDto> getPosts() {
        try {
            List<Post> posts = postRepository.findAll(Sort.by(Sort.Direction.DESC, "idx"));
            List<PostDto> postList = new ArrayList<>();

            for (Post post : posts) {
                UserDto userDto = new UserDto(post.getUser());

                List<CommentDto> commentDtoList = new ArrayList<>();
                for (Comment comment : post.getComments()) {
                    commentDtoList.add(new CommentDto(comment));
                }

                PostDto postDto = new PostDto(post, userDto, commentDtoList);

                postList.add(postDto);
            }

            return postList;
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");
        }
    }

    @Transactional(readOnly = true)
    public List<PostDto> getPostsTitle(String q) {
        try {
            List<Post> posts = postRepository.findAllByTitleContaining(q, Sort.by(Sort.Direction.DESC, "idx"));
            List<PostDto> postList = new ArrayList<>();

            for (Post post : posts) {
                UserDto userDto = new UserDto(post.getUser());

                List<CommentDto> commentDtoList = new ArrayList<>();
                for (Comment comment : post.getComments()) {
                    commentDtoList.add(new CommentDto(comment));
                }

                PostDto postDto = new PostDto(post, userDto, commentDtoList);

                postList.add(postDto);
            }

            return postList;
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");
        }
    }

    @Transactional(readOnly = true)
    public List<PostDto> getPostsUser(String q) {
        try {
            User user = userRepo.findUserByNameContaining(q).orElseThrow(
                    () -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "그딴거 없다")
            );
            List<Post> posts = postRepository.findAllByUser(user, Sort.by(Sort.Direction.DESC, "idx"));
            List<PostDto> postList = new ArrayList<>();

            for (Post post : posts) {
                UserDto userDto = new UserDto(post.getUser());

                List<CommentDto> commentDtoList = new ArrayList<>();
                for (Comment comment : post.getComments()) {
                    commentDtoList.add(new CommentDto(comment));
                }

                PostDto postDto = new PostDto(post, userDto, commentDtoList);

                postList.add(postDto);
            }

            return postList;
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");
        }
    }
}
