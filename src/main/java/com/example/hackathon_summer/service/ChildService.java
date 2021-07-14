package com.example.hackathon_summer.service;

import com.example.hackathon_summer.domain.dto.child.data.ChildDetailDto;
import com.example.hackathon_summer.domain.dto.child.data.ChildListDto;
import com.example.hackathon_summer.domain.dto.child.request.ChildProfileDto;
import com.example.hackathon_summer.domain.dto.file.FileDto;
import com.example.hackathon_summer.domain.entity.Child;
import com.example.hackathon_summer.domain.entity.ChildFileCon;
import com.example.hackathon_summer.domain.entity.File;
import com.example.hackathon_summer.domain.entity.User;
import com.example.hackathon_summer.domain.repository.ChildFileConRepo;
import com.example.hackathon_summer.domain.repository.ChildRepo;
import com.example.hackathon_summer.domain.repository.FileRepo;
import com.example.hackathon_summer.domain.repository.UserRepo;
import com.example.hackathon_summer.enums.SearchMode;
import com.example.hackathon_summer.enums.Sex;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChildService {
    private final ChildRepo childRepository;
    private final ChildFileConRepo childFileConRepo;
    private final FileRepo fileRepo;
    private final UserRepo userRepo;
    private final FileService fileService;
    private final MultipartService multipartService;

    @Transactional
    public void createChild(ChildProfileDto childProfileDto, User author) {
        try {
            FileDto fileDto = multipartService.uploadSingle(childProfileDto.getFile());
            File file = fileService.createFile(fileDto);

            Child child = childProfileDto.toEntity(file, author);
            childRepository.save(child);

            ChildFileCon con = new ChildFileCon(child, file);

            childFileConRepo.save(con);

        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");
        }
    }

    @Transactional(readOnly = true)
    public List<ChildListDto> getChildren() {
        try {
            List<Child> randomChildren = childRepository.findAllRandom();

            List<ChildListDto> childList = new ArrayList<>();

            for (Child randomChild : randomChildren) {
                String area = randomChild.getUser().getArea();
                File file = randomChild.getUsers().getFile();
                FileDto fileDto = new FileDto();
                fileDto.setFileLocation(file.getFileLocation());
                fileDto.setType(fileDto.getType());
                ChildListDto dto = new ChildListDto(randomChild, area, fileDto);
                childList.add(dto);
            }

            return childList;

        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");
        }
    }

    @Transactional(readOnly = true)
    public ChildDetailDto getChildDetail(Long id) {
        try {
            Child child = childRepository.findById(id).orElseThrow(
                    () -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "없는 아이입니다.")
            );

            User user = child.getUser();
            File file = child.getUsers().getFile();
            FileDto fileDto = new FileDto();
            fileDto.setType(file.getFileType());
            fileDto.setFileLocation(file.getFileLocation());

            return new ChildDetailDto(child, user, fileDto);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");
        }
    }

    @Transactional(readOnly = true)
    public List<ChildListDto> searchName(String q) {
        try {
            List<Child> children = childRepository.findAllByNameContaining(q);

            List<ChildListDto> childList = new ArrayList<>();

            if (children.isEmpty()) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "검색 결과를 찾을 수 잆습니다.");
            }

            for (Child child : children) {
                String area = child.getUser().getArea();
                File file = child.getUsers().getFile();
                FileDto fileDto = new FileDto();
                fileDto.setFileLocation(file.getFileLocation());
                fileDto.setType(fileDto.getType());
                ChildListDto dto = new ChildListDto(child, area, fileDto);
                childList.add(dto);
            }

            return childList;
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");
        }
    }

    @Transactional(readOnly = true)
    public List<ChildListDto> searchAge(int q) {
        try {
            List<Child> children = childRepository.findAllByAge(q);

            return this.getChildList(children);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");
        }
    }

    @Transactional(readOnly = true)
    public List<ChildListDto> searchSex(Sex q) {
        try {
            List<Child> children = childRepository.findAllBySex(q);

            return this.getChildList(children);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");
        }
    }

    @Transactional(readOnly = true)
    public List<ChildListDto> searchArea(String q) {
        try {
            List<Child> children = childRepository.findAllByArea(q);

            return this.getChildList(children);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");
        }
    }

    public List<ChildListDto> getChildList(List<Child> children) {
        try {
            List<ChildListDto> childList = new ArrayList<>();

            if (children.isEmpty()) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "검색 결과를 찾을 수 잆습니다.");
            }

            for (Child child : children) {
                String area = child.getUser().getArea();
                File file = child.getUsers().getFile();
                FileDto fileDto = new FileDto();
                fileDto.setFileLocation(file.getFileLocation());
                fileDto.setType(fileDto.getType());
                ChildListDto dto = new ChildListDto(child, area, fileDto);
                childList.add(dto);
            }

            return childList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");
        }
    }
}
