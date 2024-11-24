package app.handong.feed.service.impl;

import app.handong.feed.constants.Permission;
import app.handong.feed.dto.TbadminDto;
import app.handong.feed.exception.NoAuthorizationException;
import app.handong.feed.id.UserPermId;
import app.handong.feed.mapper.TbadminMapper;
import app.handong.feed.repository.TbUserPermRepository;
import app.handong.feed.service.FirebaseService;
import app.handong.feed.service.TbadminService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TbadminServiceImpl implements TbadminService {
    private final TbadminMapper tbadminMapper;
    private final TbUserPermRepository tbUserPermRepository;
    private final FirebaseService firebaseService;

    public TbadminServiceImpl(TbadminMapper tbadminMapper, TbUserPermRepository tbUserPermRepository, FirebaseService firebaseService) {
        this.tbadminMapper = tbadminMapper;
        this.tbUserPermRepository = tbUserPermRepository;
        this.firebaseService = firebaseService;
    }

    public List<TbadminDto.UserDetail> adminGetUser(String userId, Map<String, String> param) {
        throwIfNotAdmin(userId, Permission.ADMIN_GET_USER);
        return tbadminMapper.allUsers();
    }

    public List<String> adminGetFirebaseStorageList(String userId) {

        throwIfNotAdmin(userId, Permission.ADMIN_FIREBASE_FILES);
        return firebaseService.listAllFiles("KaFile");
    }

    @Override
    public void throwIfNotAdmin(String userId, Permission permission) {
        if (tbUserPermRepository.findById(new UserPermId(userId, permission.getValue())).isEmpty())
            throw new NoAuthorizationException("No Admin Permission");
    }
}
