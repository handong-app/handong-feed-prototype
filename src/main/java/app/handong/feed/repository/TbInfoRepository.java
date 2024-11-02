package app.handong.feed.repository;

import app.handong.feed.domain.TbInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TbInfoRepository extends JpaRepository<TbInfo, String> {
    TbInfo findByUid(String uid);
}
