package app.handong.feed.service.impl;

import app.handong.feed.dto.DefaultDto;
import app.handong.feed.dto.TblostItemFileDto;
import app.handong.feed.repository.TblostItemFileRepository;
import app.handong.feed.service.TblostItemFileService;
import org.springframework.stereotype.Service;


@Service
public class TblostItemFileServiceImpl implements TblostItemFileService {

    private final TblostItemFileRepository tblostitemFileRepository;

    public TblostItemFileServiceImpl(TblostItemFileRepository tblostitemFileRepository) {
        this.tblostitemFileRepository = tblostitemFileRepository;
    }

    @Override
    public DefaultDto.IdResDto createLostItemFile(TblostItemFileDto.CreateServDto createServDto){
        return DefaultDto.IdResDto.builder().id(tblostitemFileRepository.save(createServDto.toEntity()).getId()).build();
    }

}
