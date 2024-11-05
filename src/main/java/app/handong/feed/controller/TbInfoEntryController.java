package app.handong.feed.controller;


import app.handong.feed.domain.TbInfoEntry;
import app.handong.feed.dto.TbInfoEntryDto;
import app.handong.feed.repository.TbInfoEntryRepository;
import app.handong.feed.service.TbInfoEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/read/{tbinfoid}")
    public Optional<TbInfoEntry> readInfoEntry(@PathVariable int tbinfoid) {
        // Retrieve the TbInfoEntry from the repository, handling Optional
        Optional<TbInfoEntry> createdInfoOpt = tbInfoEntryRepository.findByTbInfoId(tbinfoid);
        // Check if the entry is present, and return either the data or a NOT_FOUND response
        return createdInfoOpt;
    }
    @GetMapping("/read/all")
    public ResponseEntity<List<TbInfoEntry>> readAllInfoEntries() {
        List<TbInfoEntry> infoEntries = tbInfoEntryRepository.findAll();
        if (infoEntries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(infoEntries, HttpStatus.OK);
    }



    @PutMapping("/update")
    public ResponseEntity<TbInfoEntryDto.InfoEntryCreateReqDto> updateInfoEntry(@RequestBody TbInfoEntryDto.InfoEntryCreateReqDto param) {

        TbInfoEntryDto.InfoEntryCreateReqDto createdInfo = tbInfoEntryService.updateInfo(param);
        return new ResponseEntity<>(createdInfo, HttpStatus.CREATED);
    }



}
