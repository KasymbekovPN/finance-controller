// TODO: 19.07.2022 del
//package kpn.financecontroller.data.services.dto;
//
//import com.querydsl.core.types.Predicate;
//import kpn.financecontroller.data.services.dto.deleters.DeleterOld;
//import kpn.financecontroller.data.services.dto.executors.PredicateExecutorOld;
//import kpn.financecontroller.data.services.dto.loaders.LoaderOld;
//import kpn.financecontroller.data.services.dto.savers.SaverOld;
//import lombok.AllArgsConstructor;
//
//// TODO: 14.07.2022 del
//@AllArgsConstructor
//public class DTOServiceOLdImplOld<DOMAIN, ENTITY> implements DTOServiceOLdOld<DOMAIN, ENTITY> {
//    private final SaverOld<DOMAIN, ENTITY, Long> saverOld;
//    private final LoaderOld<DOMAIN, ENTITY, Long> loaderOld;
//    private final DeleterOld<DOMAIN, ENTITY, Long> deleterOld;
//    private final PredicateExecutorOld<DOMAIN, Predicate> executor;
//
//    @Override
//    public SaverOld<DOMAIN, ENTITY, Long> saver() {
//        return saverOld;
//    }
//
//    @Override
//    public LoaderOld<DOMAIN, ENTITY, Long> loader() {
//        return loaderOld;
//    }
//
//    @Override
//    public DeleterOld<DOMAIN, ENTITY, Long> deleter() {
//        return deleterOld;
//    }
//
//    @Override
//    public PredicateExecutorOld<DOMAIN, Predicate> executor() {
//        return executor;
//    }
//}
