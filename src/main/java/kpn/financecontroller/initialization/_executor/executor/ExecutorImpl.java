// TODO: 16.03.2022 del
//package kpn.financecontroller.initialization._executor.executor;
//
//import kpn.financecontroller.initialization._context.context.Context;
//import kpn.financecontroller.initialization._factory.factory.TaskFactory;
//import kpn.financecontroller.initialization._task.task.Task;
//import kpn.financecontroller.result.Result;
//
//import java.util.Optional;
//
//public class ExecutorImpl implements Executor{
//    private static final String SUCCESS_CODE = "executor.success";
//    private static final String TERMINATED_CODE = "executor.terminated";
//
//    private final Context context;
//
//    public ExecutorImpl(Context context) {
//        this.context = context;
//    }
//
//    @Override
//    public Result<Void> execute(TaskFactory factory) {
//        Optional<Task> maybeNextTask;
//        boolean continuationPossible = false;
//        Result.Builder<Void> builder = Result.<Void>builder().success(true).code(SUCCESS_CODE).arg(factory.getId());
//        do {
//            maybeNextTask = factory.getNextIfExist();
//            if (maybeNextTask.isPresent()){
//                Task task = maybeNextTask.get();
//                task.execute(context);
//                continuationPossible = task.isContinuationPossible();;
//                if (!continuationPossible){
//                    builder.success(false).code(TERMINATED_CODE).arg(task.getKey());
//                }
//            }
//        } while (maybeNextTask.isPresent() && continuationPossible);
//
//        return builder.build();
//    }
//}
