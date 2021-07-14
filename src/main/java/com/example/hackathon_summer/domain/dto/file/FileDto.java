package com.example.hackathon_summer.domain.dto.file;

import com.example.hackathon_summer.domain.entity.File;
import lombok.Data;

@Data
public class FileDto {
    private String fileLocation;
    private String type;

    public File toEntity() {
        return File.builder()
                .fileLocation(fileLocation)
                .fileType(type)
                .build();
    }
}
