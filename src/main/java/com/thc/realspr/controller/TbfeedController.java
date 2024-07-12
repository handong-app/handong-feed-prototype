package com.thc.realspr.controller;

import com.thc.realspr.dto.TbfeedDto;
import com.thc.realspr.service.TbfeedService;
import com.thc.realspr.service.TbuserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/feed")
@RestController
public class TbfeedController {

    private final TbfeedService tbfeedService;

    public TbfeedController(
            TbfeedService tbfeedService
    ) {
        this.tbfeedService = tbfeedService;
    }

    @PostMapping("")
    public ResponseEntity<TbfeedDto.CreateResDto> create(@RequestBody TbfeedDto.CreateReqDto param) {
        System.out.println(param);
        return ResponseEntity.status(HttpStatus.CREATED).body(tbfeedService.create(param));
    }

    @PutMapping("")
    public Map<String, Object> update(@RequestBody Map<String, Object> param) {
        System.out.println(param);
        return tbfeedService.update(param);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {
        tbfeedService.delete(id);
        return "";
    }

    @GetMapping("/get")
    public ResponseEntity<List<TbfeedDto.SelectResDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(tbfeedService.getAll());
    }

// list -> 다중필터기능
//    @GetMapping("/list")
//    public ResponseEntity<List<TbfeedDto.SelectResDto>> getAll(@Valid TbfeedDto.ListReqDto param) {
//        return ResponseEntity.status(HttpStatus.OK).body(tbfeedService.list(param));
//    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TbfeedDto.SelectResDto> detail(@PathVariable("id") String id) {
        System.out.println(id);
        return ResponseEntity.status(HttpStatus.OK).body(tbfeedService.get(id));
    }
}
