package app.handong.feed.controller.page;

import app.handong.feed.dto.TbmessageDto;
import app.handong.feed.service.ShortHashService;
import app.handong.feed.service.TbKaFeedService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/k")
@Controller
public class ShareMetaPageController {


    private final TbKaFeedService tbKaFeedService;

    public ShareMetaPageController(
            TbKaFeedService tbKaFeedService) {
        this.tbKaFeedService = tbKaFeedService;
    }

    @GetMapping("/{hash}")
    public String page(@PathVariable("hash") String hash, Model model) {
        TbmessageDto.Detail detail = tbKaFeedService.getOneHash(hash);
        if (detail == null) return "sharemeta_notfound.html";
        String message = detail.getMessage().replaceAll("\\s+", " ");
        model.addAttribute("title", message.substring(0, Integer.min(message.length(), 100)));
        if (detail.getFiles() != null && !detail.getFiles().isEmpty())
            model.addAttribute("imageUrl", detail.getFiles().get(0));
        else model.addAttribute("imageUrl", "https://feed.handong.app/og-image.jpg");
        model.addAttribute("finalUrl", "https://feed.handong.app/kafeed/" + detail.getId());
        model.addAttribute("hashUrl", "https://feed.handong.app/k/" + hash);
        return "sharemeta.html";
    }


}
