// TODO: 16.03.2022 del
//package kpn.financecontroller.initialization._old.old.save.managers.saving;
//
//import kpn.financecontroller.builders.Builder;
//import kpn.financecontroller.checkers.GroupChecker;
//import kpn.financecontroller.converters.Converter;
//import kpn.financecontroller.data.domains.tag.Tag;
//import kpn.financecontroller.data.entities.tag.TagEntity;
//import kpn.financecontroller.data.services.DTOService;
//import kpn.financecontroller.initialization._old.old.collectors.LoadDataCollector;
//import kpn.financecontroller.initialization._old.old.entities.TagInitialEntity;
//import kpn.financecontroller.initialization._old.old.save.updaters.CollectorUpdater;
//import kpn.financecontroller.result.Result;
//import lombok.Setter;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
//// TODO: 27.02.2022 del ???
//// TODO: 27.02.2022 del ???
//@Service
//@Profile("dev")
//public class TagSaveManager extends AbstractSaveManager<Long, TagInitialEntity>{
//
//    private static final String ID = "TAGS";
//
//    private final DTOService<Tag, TagEntity, Long> dtoService;
//    private final Converter<TagInitialEntity, TagEntity> converter;
//    private final Builder<Object, String> concatBuilder;
//    private final CollectorUpdater<Long, TagInitialEntity> collectorUpdater;
//
//    @Setter
//    private LoadDataCollector<Long, TagInitialEntity> collector;
//
//
//    public TagSaveManager(GroupChecker<LoadDataCollector<?, ?>> collectorChecker,
//                          DTOService<Tag, TagEntity, Long> dtoService,
//                          Converter<TagInitialEntity, TagEntity> converter,
//                          Builder<Object, String> concatBuilder,
//                          CollectorUpdater<Long, TagInitialEntity> collectorUpdater) {
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
//        Map<Long, TagInitialEntity> entities = collector.getEntities();
//        for (Map.Entry<Long, TagInitialEntity> entry : entities.entrySet()) {
//            TagEntity tagEntity = converter.convert(entry.getValue());
//            Result<Tag> saveResult = dtoService.saver().save(tagEntity);
//            if (saveResult.getSuccess()){
//                Tag tag = saveResult.getValue();
//                collectorUpdater.add(entry.getKey(), tag.getId());
//            } else {
//                success = false;
//                concatBuilder.append(entry.getKey());
//            }
//        }
//
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
