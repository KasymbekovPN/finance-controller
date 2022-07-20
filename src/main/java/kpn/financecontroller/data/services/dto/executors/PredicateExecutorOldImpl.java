// TODO: 19.07.2022 del
//package kpn.financecontroller.data.services.dto.executors;
//
//import com.querydsl.core.types.Predicate;
//import kpn.lib.result.ImmutableResult;
//import kpn.lib.result.Result;
//import org.springframework.data.querydsl.QuerydslPredicateExecutor;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.Function;
//
//// TODO: 14.07.2022 del
//public class PredicateExecutorOldImpl<DOMAIN, ENTITY> implements PredicateExecutorOld<DOMAIN, Predicate> {
//
//    private final String name;
//    private final QuerydslPredicateExecutor<ENTITY> repo;
//    private final Function<List<ENTITY>, List<DOMAIN>> toDomains;
//
//    public PredicateExecutorOldImpl(String name, QuerydslPredicateExecutor<ENTITY> repo, Function<List<ENTITY>, List<DOMAIN>> toDomains) {
//        this.name = name;
//        this.repo = repo;
//        this.toDomains = toDomains;
//    }
//
//    @Override
//    public Result<List<DOMAIN>> execute(Predicate predicate) {
//        ImmutableResult.Builder<List<DOMAIN>> builder;
//        try{
//            ArrayList<ENTITY> entities = new ArrayList<>();
//            repo.findAll(predicate).forEach(entities::add);
//            builder = ImmutableResult.<List<DOMAIN>>bOk(toDomains.apply(entities));
//        } catch (Throwable t){
//            builder = ImmutableResult.<List<DOMAIN>>bFail("executor.execution.fail");
//        }
//        return builder.arg(name).build();
//    }
//}
