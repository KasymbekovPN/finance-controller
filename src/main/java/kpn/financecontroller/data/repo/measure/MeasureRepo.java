package kpn.financecontroller.data.repo.measure;

import kpn.financecontroller.data.entities.measure.MeasureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MeasureRepo extends JpaRepository<MeasureEntity, Long> {
    List<MeasureEntity> findByCode(String code);

    @Query("select c from measure c where c.code like concat('%', :filter, '%')")
    List<MeasureEntity> search(@Param("filter") String filter);
}
