package app.handong.feed.controller;

import app.handong.feed.dto.UserInteractionDto;
import app.handong.feed.service.UserInteractionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/feeduser")
@RestController
public class UserInteractionController {

    private final UserInteractionService userInteractionService;

    public UserInteractionController(
            UserInteractionService userInteractionService
    ) {
        this.userInteractionService = userInteractionService;
    }

    @PostMapping("/seen")
    public String postSeen(@Valid @RequestBody UserInteractionDto.SeenSubjectReqDto param, HttpServletRequest request) {
        return userInteractionService.seen(param, request.getAttribute("reqUserId").toString());
    }

    @PostMapping("/like")
    public String postLike(@Valid @RequestBody UserInteractionDto.SeenSubjectReqDto param, HttpServletRequest request) {
        return userInteractionService.like(param, request.getAttribute("reqUserId").toString());
    }

    @PostMapping("/unlike")
    public String postUnLike(@Valid @RequestBody UserInteractionDto.SeenSubjectReqDto param, HttpServletRequest request) {
        return userInteractionService.unLike(param, request.getAttribute("reqUserId").toString());
    }

    @PostMapping("/readall")
    public String postReadAll(@Valid @RequestBody UserInteractionDto.ReadAllReqDto param, HttpServletRequest request) {
        return userInteractionService.readAll(param, request.getAttribute("reqUserId").toString());
    }


}
