package app.handong.feed.controller;


import app.handong.feed.dto.TbInfoEntryDto;
import app.handong.feed.service.TbInfoEntryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/InfoEntry")
@RestController
public class TbInfoEntryController {

    private final TbInfoEntryService tbInfoEntryService;


    @Autowired
    public TbInfoEntryController(TbInfoEntryService tbInfoEntryService) {
        this.tbInfoEntryService = tbInfoEntryService;
    }

    @PostMapping("/create")
    public ResponseEntity<TbInfoEntryDto.InfoEntryCreateReqDto> createInfoEntry(@RequestBody TbInfoEntryDto.InfoEntryCreateReqDto param) {
        TbInfoEntryDto.InfoEntryCreateReqDto createdInfo = tbInfoEntryService.createInfo(param);
        return new ResponseEntity<>(createdInfo, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<TbInfoEntryDto.InfoEntryCreateReqDto> updateInfoEntry(@RequestBody TbInfoEntryDto.InfoEntryCreateReqDto param) {

        TbInfoEntryDto.InfoEntryCreateReqDto createdInfo = tbInfoEntryService.updateInfo(param);
        return new ResponseEntity<>(createdInfo, HttpStatus.CREATED);
    }



}
