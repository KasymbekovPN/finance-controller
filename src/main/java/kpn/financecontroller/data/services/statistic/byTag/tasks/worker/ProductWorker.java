// TODO: 30.05.2022 restore
//package kpn.financecontroller.data.services.statistic.byTag.tasks.worker;
//
//import kpn.financecontroller.data.domains.product.Product;
//import kpn.financecontroller.data.entities.product.ProductEntity;
//import kpn.financecontroller.data.services.dto.DTOService;
//import kpn.financecontroller.data.services.statistic.byTag.tasks.checker.Checker;
//import kpn.financecontroller.data.services.statistic.byTag.tasks.task.ProductTask;
//import kpn.lib.result.Result;
//
//final public class ProductWorker extends AbstractWorker<ProductTask, Product> {
//
//    private final DTOService<Product, ProductEntity> service;
//
//    public ProductWorker(Checker<ProductTask> checker, DTOService<Product, ProductEntity> service) {
//        super(checker);
//        this.service = service;
//    }
//
//    @Override
//    protected Result<Product> executeTask(ProductTask task) {
//        return null;
//    }
//
//    @Override
//    protected Result<Product> convertResult(Result<ProductTask> checkingResult) {
//        return null;
//    }
//}
