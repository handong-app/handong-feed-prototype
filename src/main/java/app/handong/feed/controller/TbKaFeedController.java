package app.handong.feed.controller;

import app.handong.feed.dto.TbmessageDto;
import app.handong.feed.service.ShortHashService;
import app.handong.feed.service.TbKaFeedService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/kafeed")
@RestController
public class TbKaFeedController {

    private final TbKaFeedService tbKaFeedService;
    private final ShortHashService shortHashService;

    public TbKaFeedController(
            TbKaFeedService tbKaFeedService,
            ShortHashService shortHashService) {
        this.tbKaFeedService = tbKaFeedService;
        this.shortHashService = shortHashService;
    }

//    @PostMapping("")
//    public Map<String, Object> create(@RequestBody Map<String, Object> param) {
//        System.out.println(param);
//        return tbKaFeedService.create(param);
//    }
//
//    @GetMapping("/last")
//    public Map<String, Object> getLastKaFeed() {
//        return tbKaFeedService.getLastKaFeed();
//    }
//
//    @GetMapping("/all")
//    public List<Map<String, Object>> getAllKaFeed() {
//        return tbKaFeedService.getAll();
//    }

    @GetMapping("/scrolllist")
    public List<TbmessageDto.Detail> getListAll(@RequestParam Map<String, String> param, HttpServletRequest request) {
        String reqUserId = request.getAttribute("reqUserId").toString();
        final String afterSentAt = param.get("afterSentAt");
        String type = param.get("type");

        if (afterSentAt != null && !afterSentAt.equals("-1"))
            return tbKaFeedService.scrollList(type, Integer.parseInt(afterSentAt), reqUserId);
        else return tbKaFeedService.scrollList(type, reqUserId);
    }

    @GetMapping("/get/{messageId}")
    public TbmessageDto.Detail getOne(@PathVariable String messageId, HttpServletRequest request) {
        String reqUserId = request.getAttribute("reqUserId").toString();
        return tbKaFeedService.getOne(messageId, reqUserId);
    }

    @GetMapping("/count")
    public TbmessageDto.Count getCount(@RequestParam Map<String, String> param, HttpServletRequest request) {
        String reqUserId = request.getAttribute("reqUserId").toString();
        final String afterSentAt = param.get("afterSentAt");
        final String all = param.get("all");

        int count = 0;

        if (all != null && all.equalsIgnoreCase("y")) reqUserId = null;

        if (afterSentAt != null && !afterSentAt.equals("-1"))
            count = tbKaFeedService.count(Integer.parseInt(afterSentAt), reqUserId);
        else count = tbKaFeedService.count(reqUserId);
        return TbmessageDto.Count.builder().count(count).build();
    }

    @GetMapping("/sharehash/{hash}")
    public TbmessageDto.ShareHash shareHash(@PathVariable String hash, HttpServletRequest request) {
        return TbmessageDto.ShareHash.builder().shortHash(shortHashService.getUniqueShortHash(hash, "TbKaMessage", "id")).hash(hash).build();
    }


}