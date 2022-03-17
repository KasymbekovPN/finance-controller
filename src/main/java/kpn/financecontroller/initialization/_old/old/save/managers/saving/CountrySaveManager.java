// TODO: 16.03.2022 del
//package kpn.financecontroller.initialization._old.old.save.managers.saving;
//
//import kpn.financecontroller.builders.Builder;
//import kpn.financecontroller.checkers.GroupChecker;
//import kpn.financecontroller.converters.Converter;
//import kpn.financecontroller.data.domains.country.Country;
//import kpn.financecontroller.data.entities.country.CountryEntity;
//import kpn.financecontroller.data.services.DTOService;
//import kpn.financecontroller.initialization._old.old.entities.CountryInitialEntity;
//import kpn.financecontroller.initialization._old.old.collectors.LoadDataCollector;
//import kpn.financecontroller.initialization._old.old.entities.TagInitialEntity;
//import kpn.financecontroller.initialization._old.old.save.updaters.CollectorUpdater;
//import kpn.financecontroller.result.Result;
//import lombok.Setter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
//// TODO: 27.02.2022 del ???
//@Service
//@Profile("dev")
//public class CountrySaveManager extends AbstractSaveManager<Long, TagInitialEntity> {
//
//    private static final String ID = "COUNTRIES";
//
//    private final DTOService<Country, CountryEntity, Long> dtoService;
//    private final Converter<CountryInitialEntity, CountryEntity> converter;
//    private final Builder<Object, String> concatBuilder;
//    private final CollectorUpdater<Long, CountryInitialEntity> collectorUpdater;
//
//    @Setter
//    private LoadDataCollector<Long, CountryInitialEntity> collector;
//
//    @Autowired
//    public CountrySaveManager(GroupChecker<LoadDataCollector<?, ?>> collectorChecker,
//                              DTOService<Country, CountryEntity, Long> dtoService,
//                              Converter<CountryInitialEntity, CountryEntity> converter,
//                              Builder<Object, String> concatBuilder,
//                              CollectorUpdater<Long, CountryInitialEntity> collectorUpdater) {
//        super(ID, collectorChecker);
//        this.dtoService = dtoService;
//        this.converter = converter;
//        this.concatBuilder = concatBuilder;
//        this.collectorUpdater = collectorUpdater;
//    }
//
//    @Override
//    protected Result<Void> checkCollectors() {
//        return collectorChecker.reset().set(ID, collector).check();
//    }
//
//    @Override
//    protected Result<Void> deleteBefore() {
//        return dtoService.deleter().all();
//    }
//
//    @Override
//    protected Result<Void> saveImpl() {
//        boolean success = true;
//        collectorUpdater.reset();
//        concatBuilder.reset();
//        Map<Long, CountryInitialEntity> entities = collector.getEntities();
//        for (Map.Entry<Long, CountryInitialEntity> entry : entities.entrySet()) {
//            CountryEntity countryEntity = converter.convert(entry.getValue());
//            Result<Country> savingResult = dtoService.saver().save(countryEntity);
//            if (savingResult.getSuccess()){
//                Country country = savingResult.getValue();
//                collectorUpdater.add(entry.getKey(), country.getId());
//            } else {
//                success = false;
//                concatBuilder.append(entry.getKey());
//            }
//        }
//        collectorUpdater.update(collector);
//
//        Result.Builder<Void> builder = Result.<Void>builder()
//                .success(success)
//                .code(success ? "save.manager.saving.success" : "save.manager.saving.fail")
//                .arg(ID);
//        if (!success){
//            builder.arg(concatBuilder.build());
//        }
//
//        return builder.build();
//    }
//
//    @Override
//    public void clearCollector() {
//        collector = null;
//    }
//}
