package app.handong.feed.repository;

import app.handong.feed.domain.TbUserLike;
import app.handong.feed.domain.TbUserReadAll;
import app.handong.feed.id.UserSubjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TbUserReadAllRepository extends JpaRepository<TbUserReadAll, String> {

}
