package app.handong.feed.service;

import app.handong.feed.constants.Permission;
import app.handong.feed.dto.TbadminDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TbadminService {
    List<TbadminDto.UserDetail> adminGetUser(String userId, Map<String, String> param);
    List<String> adminGetFirebaseStorageList(String userId);

    void throwIfNotAdmin(String userId, Permission permission);
}
