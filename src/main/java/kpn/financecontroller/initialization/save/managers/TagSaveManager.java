package kpn.financecontroller.initialization.save.managers;

import kpn.financecontroller.checkers.GroupChecker;
import kpn.financecontroller.converters.Converter;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization.collectors.LoadDataCollector;
import kpn.financecontroller.initialization.entities.TagInitialEntity;
import kpn.financecontroller.result.Result;
import lombok.Setter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Map;

@Service
@Profile("dev")
public class TagSaveManager extends AbstractSaveManager<Long, TagInitialEntity>{

    private static final String ID = "TAGS";
    private final DTOService<Tag, TagEntity, Long> dtoService;
    private final Converter<TagInitialEntity, TagEntity> converter;

    @Setter
    private LoadDataCollector<Long, TagInitialEntity> collector;


    public TagSaveManager(GroupChecker<LoadDataCollector<?, ?>> collectorChecker,
                          DTOService<Tag, TagEntity, Long> dtoService,
                          Converter<TagInitialEntity, TagEntity> converter) {
        super(ID, collectorChecker);
        this.dtoService = dtoService;
        this.converter = converter;
    }

    @Override
    protected Result<Void> checkCollectors() {
        return collectorChecker.reset().set(ID, collector).check();
    }

    // TODO: 12.02.2022 del 
    @Override
    protected boolean checkNeedDeleteBefore() {
        return true;
    }

    @Override
    protected Result<Void> deleteBefore() {
        return dtoService.deleter().all();
    }

    @Override
    protected Result<Void> saveImpl() {
        boolean success = true;
        String delimiter = "";
        StringBuilder sb = new StringBuilder();
        ArrayDeque<ChangingQueueItem<Long>> deq = new ArrayDeque<>();
        Map<Long, TagInitialEntity> entities = collector.getEntities();
        for (Map.Entry<Long, TagInitialEntity> entry : entities.entrySet()) {
            TagEntity tagEntity = converter.convert(entry.getValue());
            Result<Tag> saveResult = dtoService.saver().save(tagEntity);
            if (saveResult.getSuccess()){
                Tag tag = saveResult.getValue();
                deq.addLast(new ChangingQueueItem<>(entry.getKey(), tag.getId()));
            } else {
                success = false;
                sb.append(delimiter).append(entry.getKey());
                delimiter = ", ";
            }
        }
        return null;
    }

    @Override
    public void clearCollector() {
        collector = null;
    }
}

// TODO: 12.02.2022 del
//// TODO: 07.02.2022 add base class

//public class TagSaveManager implements SaveManager<Long, TagInitialEntity> {
//
//    private static final String ID = "TAGS";
//    private final DTOService<Tag, TagEntity, Long> dtoService;
//
//    @Setter
//    private LoadDataCollector<Long, TagInitialEntity> collector;
//
//    @Autowired
//    public TagSaveManager(DTOService<Tag, TagEntity, Long> dtoService) {
//        this.dtoService = dtoService;
//    }
//
//    @Override
//    public Result<Void> clearTarget() {
//        Result<Void> result = checkCollectors();
//        if (result.getSuccess()){
//            if (collector.getDeleteBefore()){
//                return dtoService.deleter().all();
//            } else {
//                return Result.<Void>builder()
//                        .success(true)
//                        .code("saveManager.deleteBefore.disabled")
//                        .arg(ID)
//                        .build();
//            }
//        }
//        return result;
//    }
//
//    @Override
//    public Result<Void> save() {
//        Result<Void> result = checkCollectors();
//        if (result.getSuccess()){
//
//            // TODO: 07.02.2022 impl method must ret. Result<List<id>>
//
//            Map<Long, TagInitialEntity> entities = collector.getEntities();
//            for (Map.Entry<Long, TagInitialEntity> entry : entities.entrySet()) {
//                TagEntity tag = convertTag(entry.getValue());
//                Result<Tag> savingResult = dtoService.saver().save(tag);
//                if (savingResult.getSuccess()){
//                    entry.getValue().setId(savingResult.getValue().getId());
//                } else {
//
//                }
//            }
//        }
//        return result;
//    }
//
//    @Override
//    public void clearCollector() {
//        collector = null;
//    }
//
//    // TODO: 07.02.2022 to bean
//    private Result<Void> checkCollectors() {
//        Result.Builder<Void> builder = Result.<Void>builder();
//        if (collector != null){
//            builder.success(true);
//        } else {
//            builder.success(false).code("saveManager.collector.null").arg(ID);
//        }
//        return builder.build();
//    }
//
//    // TODO: 07.02.2022 to bean
//    private TagEntity convertTag(TagInitialEntity value) {
//        TagEntity entity = new TagEntity();
//        entity.setId(value.getId());
//        entity.setName(value.getName());
//        return entity;
//    }
//}
