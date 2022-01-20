package kpn.financecontroller.data.repos.tag;

import kpn.financecontroller.data.entities.tag.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepo extends JpaRepository<TagEntity, Long> {
}
