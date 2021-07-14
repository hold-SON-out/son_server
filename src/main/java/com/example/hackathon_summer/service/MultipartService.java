package com.example.hackathon_summer.service;

import com.example.hackathon_summer.domain.dto.file.FileDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class MultipartService{
	private Path fileStorageLocation = Paths.get("static/").toAbsolutePath().normalize();
	@Value("${server.get.url}")
	String server;

	/*
	파일 한개 업로드
	 */
	public FileDto uploadSingle(MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "파일이 비었음");
		}
		//파일 이름 재 생
		String fileName = StringUtils.cleanPath(UUID.randomUUID().toString() + "-" + Objects.requireNonNull(file.getOriginalFilename()));
		try {
			if (fileName.contains("..")) {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "파일 이름 오류");
			}

			String type = fileName.substring(fileName.lastIndexOf(".") + 1);
			FileDto fileDto = new FileDto();
			//파일 타입
			String fileType = checkFileType(type);
			fileDto.setType(fileType);

			//저장할 파일 위치
			Path targetLocation = fileStorageLocation.resolve(fileName);

			//파일 생성
			File newFile = new File(targetLocation.toString());
			boolean result = newFile.createNewFile();
			//파일에 받아온 파일의 값 넣음
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			fileDto.setFileLocation("Http://" + server + "/file/see/" + fileName);

			return fileDto;
		} catch (Exception e){
			throw e;
		}
	}//adsadsad

	private String checkFileType(String type){
		if (type.equals("jpeg") || type.equals("png") || type.equals("jpg")) {
			return "image";
		} else if (type.equals("mp4") || type.equals("AVI")) {
			return "video";
		} else {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "지원하지 않는 파일 형식");
		}
	}
}
