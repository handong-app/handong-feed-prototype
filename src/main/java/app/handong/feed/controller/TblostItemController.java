package app.handong.feed.controller;

import app.handong.feed.dto.TblostItemDto;
import app.handong.feed.service.TblostItemService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/lost-item")
@RestController
public class TblostItemController {

    private final TblostItemService tblostService;

    public TblostItemController(
            TblostItemService tblostItemService
    ) {
        this.tblostService = tblostItemService;
    }

    @Operation(summary = "분실물 게시",
            description = "분실자 이름과 내용으로 분실물 게시 <br />"
                    + "@param Long lostId <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TblostDto.DetailResDto\\> <br />"
                    + "@exception 필수 파라미터 누락하였을 때 등 <br />"
                    + "@exception 404 Not Found (주제 또는 유저를 찾을 수 없는 경우) <br />"
                    + "@exception 400 Bad Request (필수 파라미터 누락 시)"
    )
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TblostItemDto.CreateResDto> createLostItem(
            @RequestPart(value = "data") TblostItemDto.CreateReqDto createReqDto,
            @RequestPart(value = "files") List<MultipartFile> files,
            HttpServletRequest request) {

        String reqUserId = request.getAttribute("reqUserId").toString();
        TblostItemDto.CreateResDto response = tblostService.createLostItem(createReqDto, files, reqUserId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
