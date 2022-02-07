package kpn.financecontroller.initialization.save;

import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization.collectors.LoadDataCollector;
import kpn.financecontroller.initialization.entities.TagInitialEntity;
import kpn.financecontroller.result.Result;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Map;

// TODO: 07.02.2022 add base class
@Service
@Profile("dev")
public class TagSaveManager implements SaveManager<Long, TagInitialEntity> {

    private static final String ID = "TAGS";
    private final DTOService<Tag, TagEntity, Long> dtoService;

    @Setter
    private LoadDataCollector<Long, TagInitialEntity> collector;

    @Autowired
    public TagSaveManager(DTOService<Tag, TagEntity, Long> dtoService) {
        this.dtoService = dtoService;
    }

    @Override
    public Result<Void> clearTarget() {
        Result<Void> result = checkCollectors();
        if (result.getSuccess()){
            if (collector.getDeleteBefore()){
                return dtoService.deleter().all();
            } else {
                return Result.<Void>builder()
                        .success(true)
                        .code("saveManager.deleteBefore.disabled")
                        .arg(ID)
                        .build();
            }
        }
        return result;
    }

    @Override
    public Result<Void> save() {
        Result<Void> result = checkCollectors();
        if (result.getSuccess()){

            // TODO: 07.02.2022 impl method must ret. Result<List<id>>

            Map<Long, TagInitialEntity> entities = collector.getEntities();
            for (Map.Entry<Long, TagInitialEntity> entry : entities.entrySet()) {
                TagEntity tag = convertTag(entry.getValue());
                Result<Tag> savingResult = dtoService.saver().save(tag);
                if (savingResult.getSuccess()){
                    entry.getValue().setId(savingResult.getValue().getId());
                } else {

                }
            }
        }
        return result;
    }

    @Override
    public void clearCollector() {
        collector = null;
    }

    // TODO: 07.02.2022 to bean
    private Result<Void> checkCollectors() {
        Result.Builder<Void> builder = Result.<Void>builder();
        if (collector != null){
            builder.success(true);
        } else {
            builder.success(false).code("saveManager.collector.null").arg(ID);
        }
        return builder.build();
    }

    // TODO: 07.02.2022 to bean
    private TagEntity convertTag(TagInitialEntity value) {
        TagEntity entity = new TagEntity();
        entity.setId(value.getId());
        entity.setName(value.getName());
        return entity;
    }
}
