// TODO: 17.07.2022 restore
//package kpn.financecontroller.config;
//
//import kpn.financecontroller.data.domains.payment.Payment;
//import kpn.financecontroller.data.domains.product.Product;
//import kpn.financecontroller.data.services.statistic.byTag.tasks.checker.Checker;
//import kpn.financecontroller.data.services.statistic.byTag.tasks.executor.TaskExecutor;
//import kpn.financecontroller.data.services.statistic.byTag.tasks.executor.TaskExecutorImpl;
//import kpn.financecontroller.data.services.statistic.byTag.tasks.task.PaymentTask;
//import kpn.financecontroller.data.services.statistic.byTag.tasks.task.ProductTask;
//import kpn.financecontroller.data.services.statistic.byTag.tasks.worker.Worker;
//import kpn.financecontroller.rfunc.RRFunction;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//@Configuration
//public class TaskExtractorConfig {
//
//    @Bean
//    public TaskExecutor<ProductTask, Product> productTaskExtractor(Checker<ProductTask> checker,
//                                                                   RRFunction<ProductTask, List<Product>> converter,
//                                                                   Worker<ProductTask, Product> worker){
//        return new TaskExecutorImpl<>(checker, converter, worker);
//    }
//
//    @Bean
//    public TaskExecutor<PaymentTask, Payment> paymentTaskExtractor(Checker<PaymentTask> checker,
//                                                                   RRFunction<PaymentTask, List<Payment>> converter,
//                                                                   Worker<PaymentTask, Payment> worker){
//        return new TaskExecutorImpl<>(checker, converter, worker);
//    }
//}
