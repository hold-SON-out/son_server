package com.example.hackathon_summer.service;

import com.example.hackathon_summer.domain.dto.file.FileDto;
import com.example.hackathon_summer.domain.entity.File;
import com.example.hackathon_summer.domain.repository.ChildRepo;
import com.example.hackathon_summer.domain.repository.FileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Service
public class FileService {
    private final Path fileStorageLocation = Paths.get("static/").toAbsolutePath().normalize();
    private final FileRepo fileRepository;

    @Transactional
    public File createFile(FileDto file) {
        try {
            file.setFileLocation(file.getFileLocation());
            file.setType(file.getType());

            File fileEntity = file.toEntity();
            fileRepository.save(fileEntity);

            return fileEntity;
        } catch (Exception e) {
            throw e;
        }
    }

    public UrlResource loadFile(String filename) {
        try {
            Path file = fileStorageLocation.resolve(filename).normalize();
            UrlResource resource = new UrlResource(file.toUri());

            if (!resource.exists())
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "없는 파일");

            return resource;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "없는 파일");
        } catch (Exception e) {
            throw e;
        }
    }
}
