package app.handong.feed.service;

import app.handong.feed.dto.UserInteractionDto;
import org.springframework.stereotype.Service;

@Service
public interface UserInteractionService {


    String seen(UserInteractionDto.SeenSubjectReqDto param, String userId);

    String like(UserInteractionDto.SeenSubjectReqDto param, String userId);

    String unLike(UserInteractionDto.SeenSubjectReqDto param, String userId);

    String readAll(UserInteractionDto.ReadAllReqDto param, String userId);
}