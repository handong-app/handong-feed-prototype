package app.handong.feed.repository;

import app.handong.feed.domain.TblostItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TblostItemRepository extends JpaRepository<TblostItem, String> {
    boolean existsTblostItemById(String id);
}
