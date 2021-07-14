package com.example.hackathon_summer.controller;

import com.example.hackathon_summer.domain.dto.child.data.ChildDetailDto;
import com.example.hackathon_summer.domain.dto.child.data.ChildListDto;
import com.example.hackathon_summer.domain.dto.child.request.ChildProfileDto;
import com.example.hackathon_summer.domain.entity.Child;
import com.example.hackathon_summer.domain.entity.User;
import com.example.hackathon_summer.domain.response.Response;
import com.example.hackathon_summer.domain.response.ResponseData;
import com.example.hackathon_summer.enums.Purpose;
import com.example.hackathon_summer.enums.SearchMode;
import com.example.hackathon_summer.enums.Sex;
import com.example.hackathon_summer.service.ChildService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/child")
public class ChildController {
    @Autowired
    private ChildService childService;

    @PostMapping
    public Response createChildProfile(@ModelAttribute @Valid ChildProfileDto childProfileDto,
                                       HttpServletRequest request) {

        log.info("createChildProfile() 실행됩니다");
        log.info(childProfileDto.toString());
        User author = (User) request.getAttribute("user");
        if (author == null) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "토큰이 필요함");
        } else if (author.getPurpose() != Purpose.ADOPTED) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "권한 없음");
        }

        childService.createChild(childProfileDto, author);

        Response response = new Response();

        response.setStatus(HttpStatus.OK);
        response.setMassage("저장 성공");

        return response;

    }

    @GetMapping("/")
    public ResponseData<List<ChildListDto>> getChildren() {
        List<ChildListDto> children = childService.getChildren();

        ResponseData<List<ChildListDto>> responseData = new ResponseData<>();
        responseData.setStatus(HttpStatus.OK);
        responseData.setMassage("조회 성공");
        responseData.setData(children);

        return responseData;
    }

    @GetMapping("/{id}")
    public ResponseData<ChildDetailDto> getChildDetail(@PathVariable Long id) {
        ChildDetailDto childDetail = childService.getChildDetail(id);

        ResponseData<ChildDetailDto> responseData = new ResponseData<>();
        responseData.setStatus(HttpStatus.OK);
        responseData.setMassage("조회 성공");
        responseData.setData(childDetail);

        return responseData;
    }

    @GetMapping("/search/name")
    public ResponseData<List<ChildListDto>> searchChildrenName(@RequestParam String q) {
        List<ChildListDto> children = childService.searchName(q);

        ResponseData<List<ChildListDto>> responseData = new ResponseData<>();
        responseData.setStatus(HttpStatus.OK);
        responseData.setMassage("조회 성공");
        responseData.setData(children);

        return responseData;
    }

    @GetMapping("/search/age")
    public ResponseData<List<ChildListDto>> searchChildrenAge(@RequestParam int q) {
        List<ChildListDto> children = childService.searchAge(q);

        ResponseData<List<ChildListDto>> responseData = new ResponseData<>();
        responseData.setStatus(HttpStatus.OK);
        responseData.setMassage("조회 성공");
        responseData.setData(children);

        return responseData;
    }

    @GetMapping("/search/sex")
    public ResponseData<List<ChildListDto>> searchChildrenSex(@RequestParam Sex q) {
        List<ChildListDto> children = childService.searchSex(q);

        ResponseData<List<ChildListDto>> responseData = new ResponseData<>();
        responseData.setStatus(HttpStatus.OK);
        responseData.setMassage("조회 성공");
        responseData.setData(children);

        return responseData;
    }

    @GetMapping("/search/area")
    public ResponseData<List<ChildListDto>> searchChildrenArea(@RequestParam String q) {
        List<ChildListDto> children = childService.searchArea(q);

        ResponseData<List<ChildListDto>> responseData = new ResponseData<>();
        responseData.setStatus(HttpStatus.OK);
        responseData.setMassage("조회 성공");
        responseData.setData(children);

        return responseData;
    }
}
