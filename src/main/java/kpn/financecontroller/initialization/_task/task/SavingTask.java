// TODO: 16.03.2022 del
//package kpn.financecontroller.initialization._task.task;
//
//import kpn.financecontroller.converters.Converter;
//import kpn.financecontroller.data.domains.AbstractDomain;
//import kpn.financecontroller.data.entities.AbstractEntity;
//import kpn.financecontroller.data.services.DTOService;
//import kpn.financecontroller.initialization._collector.collector.InitialEntityCollector;
//import kpn.financecontroller.initialization._context.context.Context;
//import kpn.financecontroller.initialization._entities.entities.AbstractInitialEntity;
//import kpn.financecontroller.initialization._entityUpdater.entityUpdater.EntityUpdater;
//import kpn.financecontroller.result.Result;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//
//import java.util.Arrays;
//import java.util.Map;
//import java.util.Optional;
//
//@RequiredArgsConstructor
//public class SavingTask<IE extends AbstractInitialEntity, E extends AbstractEntity, D extends AbstractDomain> implements Task{
//
//    @Getter
//    private final String key;
//    private final Long entityId;
//    private final Converter<IE, E> converter;
//    private final DTOService<D, E, Long> dtoService;
//    private final EntityUpdater<IE> entityUpdater;
//    private final String collectorProperty;
//    @Getter
//    private boolean continuationPossible;
//
//    private InitialEntityCollector<Long, IE> collector;
//    private IE initialEntity;
//
//    @Override
//    public void execute(Context context) {
//        if (checkCollectorOrSetResult(context) && checkEntityAvailabilityOrSetResult(context)){
//            E entity = converter.convert(entityUpdater.update(context, initialEntity));
//            Result<D> savingResult = dtoService.saver().save(entity);
//            if (savingResult.getSuccess()){
//                updateMatching(context, entity, savingResult.getValue());
//                putResultIntoContext(context, true, Codes.SUCCESS.getValue(), key);
//                continuationPossible = true;
//            } else {
//                putResultIntoContext(context, false, Codes.SAVING_FAIL.getValue(), key, entityId);
//            }
//        }
//    }
//
//    private void updateMatching(Context context, E entity, D domain) {
//        Map<Long, Long> matchingMap;
//        Optional<Object> maybeMatching = context.get(key, Properties.MATCHING.getValue());
//        if (maybeMatching.isPresent()){
//            matchingMap = (Map<Long, Long>) maybeMatching.get();
//            matchingMap.put(domain.getId(), entity.getId());
//        } else {
//            matchingMap = Map.of(entity.getId(), domain.getId());
//        }
//        context.put(key, Properties.MATCHING.getValue(), matchingMap);
//    }
//
//    private boolean checkEntityAvailabilityOrSetResult(Context context) {
//        Optional<IE> maybeEntity = collector.getEntity(entityId);
//        if (maybeEntity.isEmpty()){
//            putResultIntoContext(context, false, Codes.NO_ONE_ENTITY.getValue(), key);
//            return false;
//        }
//        initialEntity = maybeEntity.get();
//        return true;
//    }
//
//    private boolean checkCollectorOrSetResult(Context context) {
//        Optional<Object> maybeCollector = context.get(key, collectorProperty);
//        if (maybeCollector.isEmpty()){
//            putResultIntoContext(context, false, Codes.NO_COLLECTOR.getValue(), key);
//            return false;
//        }
//        collector = (InitialEntityCollector<Long, IE>) maybeCollector.get();
//        return true;
//    }
//
//    private void putResultIntoContext(Context context, boolean success, String code, Object... args){
//        Result.Builder<Void> builder = Result.<Void>builder().success(success).code(code);
//        Arrays.stream(args).forEach(builder::arg);
//        context.put(key, Properties.RESULT.getValue(), builder.build());
//    }
//
//    @RequiredArgsConstructor
//    @Getter
//    public enum Properties{
//        RESULT("task.saving.property.result"),
//        MATCHING("task.saving.property.matching");
//
//        private final String value;
//    }
//
//    @RequiredArgsConstructor
//    @Getter
//    public enum Codes{
//        NO_COLLECTOR("task.saving.property.noCollector"),
//        NO_ONE_ENTITY("task.saving.property.noOneEntity"),
//        SAVING_FAIL("task.saving.property.savingFail"),
//        SUCCESS("task.saving.property.success");
//
//        private final String value;
//    }
//}
