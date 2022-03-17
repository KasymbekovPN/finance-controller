// TODO: 16.03.2022 del
//package kpn.financecontroller.initialization._task.task;
//
//import kpn.financecontroller.data.services.DTOService;
//import kpn.financecontroller.initialization._context.context.Context;
//import kpn.financecontroller.result.Result;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//public class ClearingTask implements Task{
//
//    @Getter
//    private final String key;
//    private final DTOService<?, ?, Long> dtoService;
//
//    @Getter
//    private boolean continuationPossible;
//
//    @Override
//    public void execute(Context context) {
//        Result<Void> result = dtoService.deleter().all();
//        context.put(key, Properties.RESULT.getValue(), result);
//        continuationPossible = result.getSuccess();
//    }
//
//    @RequiredArgsConstructor
//    @Getter
//    public enum Properties{
//        RESULT("task.clearing.property.result");
//
//        private final String value;
//    }
//}
