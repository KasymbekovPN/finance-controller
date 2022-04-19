package kpn.financecontroller.initialization.tasks.saving;

//import kpn.financecontroller.data.domains.tag.Tag;
//import kpn.financecontroller.data.entities.tag.TagEntity;
//import kpn.financecontroller.data.services.DTOService;
//import kpn.financecontroller.initialization.generators.valued.*;
//import kpn.financecontroller.initialization.managers.context.ResultContextManager;
//import kpn.financecontroller.initialization.storage.ObjectStorage;
//import kpn.financecontroller.initialization.tasks.BaseTask;
//import kpn.lib.result.Result;
//import kpn.taskexecutor.lib.contexts.Context;
//import lombok.Setter;
//
//// TODO: 18.04.2022 del
//// TODO: 07.04.2022 generate it
//final public class TagSavingTask extends BaseTask {
//    @Setter
//    private DTOService<Tag, TagEntity, Long> dtoService;
//    @Setter
//    private Long entityId;
//
//    @Override
//    public void execute(Context context) {
//        reset();
//        ResultContextManager contextManager = createContextManager(context);
//        Result<ObjectStorage> storageResult = contextManager.get(key, Properties.JSON_TO_DB_CONVERSION_RESULT, ObjectStorage.class);
//        if (storageResult.isSuccess()){
//            ObjectStorage storage = storageResult.getValue();
//            TagEntity tagEntity = (TagEntity) storage.get(entityId);
//            if (tagEntity != null){
//                Result<Tag> savingResult = dtoService.saver().save(tagEntity);
//                if (savingResult.isSuccess()){
//                    tagEntity.setId(savingResult.getValue().getId());
//                    continuationPossible = true;
//                } else {
//                    calculateAndSetCode(key, Codes.FAIL_SAVING_ATTEMPT);
//                }
//            } else {
//                calculateAndSetCode(key, Codes.ENTITY_NOT_EXIST_ON_SAVING);
//            }
//        } else {
//            calculateAndSetCode(key, Codes.CONVERSION_RESULT_NOT_EXIST_ON_SAVING);
//        }
//
//        putResultIntoContext(context, Properties.SAVING_RESULT, null);
//    }
//}
