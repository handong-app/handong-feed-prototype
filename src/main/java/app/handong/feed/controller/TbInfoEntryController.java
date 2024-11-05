package app.handong.feed.controller;


import app.handong.feed.domain.TbInfoEntry;
import app.handong.feed.dto.TbInfoDto;
import app.handong.feed.dto.TbInfoEntryDto;
import app.handong.feed.repository.TbInfoEntryRepository;
import app.handong.feed.service.TbInfoEntryService;
import app.handong.feed.service.TbInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/InfoEntry")
@RestController
public class TbInfoEntryController {


    private final TbInfoEntryService tbInfoEntryService;
    private final TbInfoEntryRepository tbInfoEntryRepository;




    @Autowired
    public TbInfoEntryController(TbInfoEntryService tbInfoEntryService, TbInfoEntryRepository tbInfoEntryRepository) {
        this.tbInfoEntryService = tbInfoEntryService;
        this.tbInfoEntryRepository = tbInfoEntryRepository;

    }

    @PostMapping("/create")
    public ResponseEntity<TbInfoEntryDto.InfoEntryCreateReqDto> createInfoEntry(@RequestBody TbInfoEntryDto.InfoEntryCreateReqDto param) {
        TbInfoEntryDto.InfoEntryCreateReqDto createdInfo = tbInfoEntryService.createInfo(param);
        return new ResponseEntity<>(createdInfo, HttpStatus.CREATED);
    }

    @GetMapping("/read/{tbinfoid}")
    public TbInfoEntry readInfoEntry(@PathVariable int tbinfoid) {
        TbInfoEntry createdInfoOpt = tbInfoEntryRepository.findByTbInfoId(tbinfoid);
        return createdInfoOpt;
    }

    @PutMapping("/update/{tbinfoid}")
    public TbInfoEntry readInfoEntry(@PathVariable int tbinfoid, @RequestBody TbInfoEntryDto.InfoEntryCreateReqDto param) {
        TbInfoEntry createdInfoOpt = tbInfoEntryService.updateInfo(tbinfoid, param).toEntity();
        return createdInfoOpt;
    }






}
