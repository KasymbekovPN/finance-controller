package kpn.financecontroller.data.services.measure;

import kpn.financecontroller.data.domains.measure.Measure;
import kpn.financecontroller.data.entities.measure.MeasureEntity;
import kpn.financecontroller.data.repos.measure.MeasureRepo;
import kpn.financecontroller.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MeasureService {

    private final MeasureRepo measureRepo;

    @Autowired
    public MeasureService(MeasureRepo measureRepo) {
        this.measureRepo = measureRepo;
    }

    public Result<Measure> save(MeasureEntity entity) {
        try{
            measureRepo.save(entity);
            return Result.<Measure>builder()
                    .success(true)
                    .value(new Measure(entity))
                    .build();
        } catch (Throwable ex){
            // TODO: 05.01.2022 extend definition
            return Result.<Measure>builder()
                    .success(false)
                    .code("service.measure.save.fail")
                    .build();
        }
    }

    public Result<Measure> loadById(Long id) {
        Optional<MeasureEntity> maybeEntity = measureRepo.findById(id);
        if (maybeEntity.isPresent()){
            return Result.<Measure>builder()
                    .success(true)
                    .value(new Measure(maybeEntity.get()))
                    .build();
        }
        return Result.<Measure>builder()
                .success(false)
                .code("service.measure.loadById.noOne")
                .arg(id)
                .build();
    }

    public void deleteById(Long id) {
        measureRepo.deleteById(id);
    }

    public Result<List<Measure>> findAll() {
        List<MeasureEntity> entities = measureRepo.findAll();
        List<Measure> measures = entities.stream().map(Measure::new).collect(Collectors.toList());
        return Result.<List<Measure>>builder().success(true).value(measures).build();
    }

    public Result<List<Measure>> search(String filter) {
        if (filter == null || filter.isEmpty()){
            return findAll();
        }
        List<MeasureEntity> entities = measureRepo.search(filter);
        List<Measure> measures = entities.stream().map(Measure::new).collect(Collectors.toList());
        return Result.<List<Measure>>builder().success(true).value(measures).build();
    }
}
