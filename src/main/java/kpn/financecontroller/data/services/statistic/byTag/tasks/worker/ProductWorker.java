//package kpn.financecontroller.data.services.statistic.byTag.tasks.worker;
//
//import kpn.financecontroller.data.domains.product.Product;
//import kpn.financecontroller.data.entities.product.ProductEntity;
//import kpn.financecontroller.data.services.dto.DTOService;
//import kpn.financecontroller.data.services.statistic.byTag.tasks.checker.Checker;
//import kpn.financecontroller.data.services.statistic.byTag.tasks.task.ProductTask;
//import kpn.financecontroller.rfunc.RRFunction;
//import kpn.lib.result.Result;
//
//// TODO: 31.05.2022 it's component
//final public class ProductWorker extends AbstractWorker<ProductTask, Product> {
//
//    private final DTOService<Product, ProductEntity> service;
//
//    public ProductWorker(Checker<ProductTask> checker,
//                         RRFunction<ProductTask, Product> converter,
//                         DTOService<Product, ProductEntity> service) {
//        super(checker, converter);
//        this.service = service;
//    }
//
//    @Override
//    protected Result<Product> executeTask(ProductTask task) {
//        return null;
//    }
//}
