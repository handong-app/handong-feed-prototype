package app.handong.feed.controller;


import app.handong.feed.dto.TbInfoDto;
import app.handong.feed.dto.TbInfoEntryDto;
import app.handong.feed.repository.TbInfoEntryRepository;
import app.handong.feed.service.TbInfoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/Info")
@RestController
public class TbInfoController {

    @Autowired
    private final TbInfoService tbInfoService;


    @Autowired
    public TbInfoController(TbInfoService tbInfoService) {
        this.tbInfoService = tbInfoService;
    }

    @PostMapping("/create")
    public ResponseEntity<TbInfoDto.InfoCreateReqDto> createInfo(@RequestBody TbInfoDto.InfoCreateReqDto param) {
        TbInfoDto.InfoCreateReqDto createdInfo = tbInfoService.createInfo(param);
        return new ResponseEntity<>(createdInfo, HttpStatus.CREATED);
    }

    @GetMapping("/read/{uid}")
    public ResponseEntity<TbInfoDto.InfoCreateReqDto> readInfo(@PathVariable String uid) {
        try {
            // Retrieve the info by UID
            TbInfoDto.InfoCreateReqDto infoDto = tbInfoService.getInfo_Id(uid);

            // Return the result if found
            return new ResponseEntity<>(infoDto, HttpStatus.OK);
        } catch (Exception e) {
            // Handle not found or other issues
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{uid}")
    public ResponseEntity<TbInfoDto.InfoCreateReqDto> updateInfo(@PathVariable String uid, @RequestBody TbInfoDto.InfoCreateReqDto param) {
        try {
            TbInfoDto.InfoCreateReqDto updatedInfoDto = tbInfoService.updateInfo(uid, param);
            return new ResponseEntity<>(updatedInfoDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
