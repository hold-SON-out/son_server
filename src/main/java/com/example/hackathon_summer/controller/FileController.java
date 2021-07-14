package com.example.hackathon_summer.controller;

import com.example.hackathon_summer.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
@RequestMapping("/file")
public class FileController {
    private final FileService fileService;

    @GetMapping("/see/{filename}")
    public ResponseEntity<UrlResource> getImage(@PathVariable String filename, HttpServletRequest request) {
        try {
            UrlResource resource = fileService.loadFile(filename);
            String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");
        }
    }
}
