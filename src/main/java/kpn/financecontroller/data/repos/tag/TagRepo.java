package kpn.financecontroller.data.repos.tag;

import kpn.financecontroller.data.entities.tag.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TagRepo extends JpaRepository<TagEntity, Long>, QuerydslPredicateExecutor<TagEntity> {
}
