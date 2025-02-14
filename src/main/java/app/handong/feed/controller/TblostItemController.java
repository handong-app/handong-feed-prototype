package app.handong.feed.controller;

import app.handong.feed.dto.DefaultDto;
import app.handong.feed.dto.TblostItemDto;
import app.handong.feed.service.TblostItemService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/lost-item")
@RestController
public class TblostItemController {

    private final TblostItemService tblostItemService;

    public TblostItemController(
            TblostItemService tblostItemService
    ) {
        this.tblostItemService = tblostItemService;
    }

    @Operation(summary = "분실물 게시",
            description = "분실자 이름과 내용으로 분실물 게시 <br />"
                    + "@param String lostId <br />"
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
        TblostItemDto.CreateResDto response = tblostItemService.createLostItem(createReqDto, files, reqUserId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "분실물 조회",
            description = "분실물 ID로 분실물 정보를 조회 <br />"
                    + "@param String lostId <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TblostDto.DetailResDto\\> <br />"
                    + "@exception 404 Not Found (분실물을 찾을 수 없는 경우)"
    )
    @GetMapping(value = "/{lostId}")
    public ResponseEntity<TblostItemDto.DetailResDto> getLostItem(
            @PathVariable("lostId") String lostId) {
        TblostItemDto.DetailResDto response = tblostItemService.getLostItemDetail(DefaultDto.IdReqDto.builder().id(lostId).build());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "분실물 전체 조회",
            description = "모든 분실물 정보를 조회 <br />"
            + "@param String lostId <br />"
            + "@return HttpStatus.OK(200) ResponseEntity\\<List<TblostItemDto.DetailResDto\\>\\> <br />"
            + "@exception 404 Not Found (분실물을 찾을 수 없는 경우)"
    )
    @GetMapping
    public ResponseEntity<List<TblostItemDto.DetailResDto>> getAllLostItems() {
        List<TblostItemDto.DetailResDto> response = tblostItemService.getAllLostItems();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "분실물 게시 수정",
            description = "분실물 게시글을 수정 <br />"
            + "@param String lostId <br />"
            + "@return HttpStatus.OK(200) ResponseEntity\\<TblostDto.DetailResDto\\> <br />"
            + "@exception 필수 파라미터 누락하였을 때 등 <br />"
            + "@exception 403 Forbidden (글쓴이가 아닐 시) <br />"
            + "@exception 404 Not Found (주제 또는 유저를 찾을 수 없는 경우) <br />"
            + "@exception 400 Bad Request (필수 파라미터 누락 시)"
    )
    @PostMapping(value = "/update/{lostId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TblostItemDto.CreateResDto> updateLostItem(
            @PathVariable("lostId") String lostId,
            @RequestPart(value = "data") TblostItemDto.UpdateReqDto updateReqDto,
            @RequestPart(value = "files") List<MultipartFile> files,
            HttpServletRequest request){

        String reqUserId = request.getAttribute("reqUserId").toString();
        TblostItemDto.CreateResDto response = tblostItemService.updateLostItem(updateReqDto.toUpdateServDto(lostId), files, reqUserId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "분실물 삭제 유저용",
            description = "작성자 확인 후 분실물 아이템을 삭제합니다. <br />"
                    + "@param String lostId <br />"
                    + "@return HttpStatus.OK(200) <br />"
                    + "@exception 404 Not Found (분실물을 찾을 수 없는 경우) <br />"
                    + "@exception 403 Forbidden (삭제 권한이 없는 경우)")
    @DeleteMapping("/{lostId}")
    public ResponseEntity<Void> deleteLostItemUser(
            @PathVariable("lostId") String lostId,
            HttpServletRequest request) {

        String reqUserId = request.getAttribute("reqUserId").toString();
        tblostItemService.deleteLostItemUser(DefaultDto.IdReqDto.builder().id(lostId).build(), reqUserId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "분실물 삭제 관리자용",
            description = "관리자 권한 확인 후분실물 아이템을 삭제합니다. <br />"
                    + "@param String lostId <br />"
                    + "@return HttpStatus.OK(200) <br />"
                    + "@exception 404 Not Found (분실물을 찾을 수 없는 경우) <br />"
                    + "@exception 403 Forbidden (삭제 권한이 없는 경우)")
    @DeleteMapping("/admin/{lostId}")
    public ResponseEntity<Void> deleteLostItemAdmin(
            @PathVariable("lostId") String lostId,
            HttpServletRequest request) {

        String reqUserId = request.getAttribute("reqUserId").toString();
        tblostItemService.deleteLostItemAdmin(DefaultDto.IdReqDto.builder().id(lostId).build(), reqUserId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
