package app.handong.feed.repository;

import app.handong.feed.domain.TblostItemFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TblostItemFileRepository extends JpaRepository<TblostItemFile, String> {
}
