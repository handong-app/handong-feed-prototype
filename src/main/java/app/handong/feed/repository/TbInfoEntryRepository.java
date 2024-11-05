package app.handong.feed.repository;

import app.handong.feed.domain.TbInfoEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface TbInfoEntryRepository extends JpaRepository<TbInfoEntry, Integer> {
    TbInfoEntry findByTbInfoId(Integer tbInfoId);
}


