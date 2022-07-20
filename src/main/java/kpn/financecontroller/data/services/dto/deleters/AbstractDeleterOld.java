// TODO: 19.07.2022 del
//package kpn.financecontroller.data.services.dto.deleters;
//
//import kpn.financecontroller.data.services.dto.DTOServiceExceptionOld;
//import kpn.lib.result.ImmutableResult;
//import kpn.lib.result.Result;
//
//import java.util.Arrays;
//
//// TODO: 14.07.2022 del
//public abstract class AbstractDeleterOld<D, E, I> implements DeleterOld<D, E, I> {
//
//    private final String name;
//
//    public AbstractDeleterOld(String name) {
//        this.name = name;
//    }
//
//    @Override
//    public Result<Void> byId(I id) {
//        ImmutableResult.Builder<Void> builder;
//        try{
//            deleteById(id);
//            builder = ImmutableResult.<Void>bOk(null);
//        } catch (DTOServiceExceptionOld ex){
//            builder = ImmutableResult.<Void>bFail(ex.getMessage());
//            Arrays.stream(ex.getArgs()).forEach(builder::arg);
//        }
//
//        return builder
//                .beginArg(id)
//                .beginArg(name)
//                .build();
//    }
//
//    @Override
//    public Result<Void> by(String attribute, Object value) {
//        ImmutableResult.Builder<Void> builder;
//        if (checkAttribute(attribute)){
//            if (checkValue(attribute, value)){
//                try{
//                    deleteBy(attribute, value);
//                    builder = ImmutableResult.<Void>bOk(null);
//                } catch (DTOServiceExceptionOld ex){
//                    builder = ImmutableResult.<Void>bFail(ex.getMessage());
//                    Arrays.stream(ex.getArgs()).forEach(builder::arg);
//                }
//            } else {
//                builder = ImmutableResult.<Void>bFail("deleter.by.value.disallowed");
//            }
//        } else {
//            builder = ImmutableResult.<Void>bFail("deleter.by.attribute.disallowed");
//        }
//
//        return builder
//                .beginArg(value)
//                .beginArg(attribute)
//                .beginArg(name)
//                .build();
//    }
//
//    @Override
//    public Result<Void> all() {
//        ImmutableResult.Builder<Void> builder;
//        try{
//            deleteAll();
//            builder = ImmutableResult.<Void>bOk(null);
//        } catch (DTOServiceExceptionOld ex){
//            builder = ImmutableResult.<Void>bFail(ex.getMessage());
//            Arrays.stream(ex.getArgs()).forEach(builder::arg);
//        }
//
//        return builder
//                .beginArg(name)
//                .build();
//    }
//
//    protected boolean checkAttribute(String attribute) {
//        return false;
//    }
//
//    protected boolean checkValue(String attribute, Object value) {
//        return false;
//    }
//
//    protected void deleteById(I id) throws DTOServiceExceptionOld {
//        throw new DTOServiceExceptionOld("deleter.deleteById.unsupported");
//    }
//
//    protected void deleteBy(String attribute, Object value) throws DTOServiceExceptionOld {
//        throw new DTOServiceExceptionOld("deleter.deleteBy.unsupported");
//    }
//
//    protected void deleteAll() throws DTOServiceExceptionOld {
//        throw new DTOServiceExceptionOld("deleter.deleteAll.unsupported");
//    }
//}
