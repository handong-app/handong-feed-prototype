package app.handong.feed.service.impl;

import app.handong.feed.domain.TbUserInteraction;
import app.handong.feed.domain.TbUserLike;
import app.handong.feed.domain.TbUserReadAll;
import app.handong.feed.dto.UserInteractionDto;
import app.handong.feed.exception.DuplicateEntityException;
import app.handong.feed.id.UserSubjectId;
import app.handong.feed.repository.TbUserInteractionRepository;
import app.handong.feed.repository.TbUserLikeRepository;
import app.handong.feed.repository.TbUserReadAllRepository;
import app.handong.feed.service.UserInteractionService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class UserInteractionServiceImpl implements UserInteractionService {


    private final TbUserInteractionRepository tbUserInteractionRepository;
    private final TbUserLikeRepository tbUserLikeRepository;
    private final TbUserReadAllRepository tbUserReadAllRepository;

    public UserInteractionServiceImpl(TbUserInteractionRepository tbUserInteractionRepository, TbUserLikeRepository tbUserLikeRepository, TbUserReadAllRepository tbUserReadAllRepository) {
        this.tbUserInteractionRepository = tbUserInteractionRepository;
        this.tbUserLikeRepository = tbUserLikeRepository;
        this.tbUserReadAllRepository = tbUserReadAllRepository;
    }

    @Override
    public String seen(UserInteractionDto.SeenSubjectReqDto param, String userId) {
        try {
            tbUserInteractionRepository.save(TbUserInteraction.of(userId, param.getSubjectId()));
        } catch (DuplicateKeyException e) {
            throw new DuplicateEntityException("User with id " + userId + " already seen " + param.getSubjectId());
        }

        return "{}";
    }

    @Override
    public String like(UserInteractionDto.SeenSubjectReqDto param, String userId) {
        try {
            tbUserLikeRepository.save(TbUserLike.of(userId, param.getSubjectId()));
        } catch (DuplicateKeyException e) {
            throw new DuplicateEntityException("User with id " + userId + " already liked " + param.getSubjectId());
        }

        return "{}";
    }

    @Override
    public String unLike(UserInteractionDto.SeenSubjectReqDto param, String userId) {
        tbUserLikeRepository.deleteById(new UserSubjectId(userId, param.getSubjectId()));
        return "{}";
    }

    @Override
    public String readAll(UserInteractionDto.ReadAllReqDto param, String userId){
        try {
            tbUserReadAllRepository.save(new TbUserReadAll(userId, param.getLastSentAt()));
        } catch (DuplicateKeyException ignored) {
        }

        return "{}";
    }

}
