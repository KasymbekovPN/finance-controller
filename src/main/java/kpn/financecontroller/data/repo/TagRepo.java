package kpn.financecontroller.data.repo;

import kpn.financecontroller.data.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TagRepo extends JpaRepository<TagEntity, Long>, QuerydslPredicateExecutor<TagEntity> {
}
