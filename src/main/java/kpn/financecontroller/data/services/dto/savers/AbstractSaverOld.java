// TODO: 19.07.2022 del
//package kpn.financecontroller.data.services.dto.savers;
//
//import kpn.financecontroller.data.services.dto.DTOServiceExceptionOld;
//import kpn.lib.result.ImmutableResult;
//import kpn.lib.result.Result;
//
//import java.util.Arrays;
//
//// TODO: 14.07.2022 del
//public abstract class AbstractSaverOld<D, E, I> implements SaverOld<D, E, I> {
//
//    private final String name;
//
//    public AbstractSaverOld(String name) {
//        this.name = name;
//    }
//
//    @Override
//    public Result<D> save(E entity) {
//        ImmutableResult.Builder<D> builder;
//        try{
//            E savedEntity = saveImpl(entity);
//            builder = ImmutableResult.<D>bOk(convertEntityToDomain(savedEntity));
//        } catch (DTOServiceExceptionOld ex){
//            builder = ImmutableResult.<D>bFail(ex.getMessage());
//            Arrays.stream(ex.getArgs()).forEach(builder::arg);
//        }
//
//        return builder
//                .beginArg(name)
//                .build();
//    }
//
//    protected E saveImpl(E entity) throws DTOServiceExceptionOld {
//        throw new DTOServiceExceptionOld("saver.saveImpl.unsupported");
//    }
//
//    protected abstract D convertEntityToDomain(E entity);
//}
