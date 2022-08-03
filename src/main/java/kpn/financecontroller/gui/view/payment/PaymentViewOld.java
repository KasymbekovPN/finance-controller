// TODO: 03.08.2022 del
//package kpn.financecontroller.gui.view.payment;
//
//import com.querydsl.core.types.Predicate;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.router.Route;
//import kpn.financecontroller.data.domains.payment.Payment;
//import kpn.financecontroller.data.domains.seller.Seller;
//import kpn.financecontroller.data.domains.product.Product;
//import kpn.financecontroller.gui.view.GridViewOld;
//import kpn.financecontroller.gui.MainLayout;
//import kpn.lib.result.Result;
//import kpn.lib.service.Service;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Scope;
//
//import javax.annotation.security.PermitAll;
//import java.util.List;
//
//// TODO: 03.08.2022 del
//@Scope("prototype")
//@Route(value = "old", layout = MainLayout.class)
//@PermitAll
//final public class PaymentViewOld extends GridViewOld<Payment> {
//    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
//            new ColumnConfig("gui.header.id", List.of("id")),
//            new ColumnConfig("gui.header.product", List.of("product", "name")),
//            new ColumnConfig("gui.header.price", List.of("price")),
//            new ColumnConfig("gui.header.currency", List.of("currency")),
//            new ColumnConfig("gui.header.amount", List.of("amount")),
//            new ColumnConfig("gui.header.measure", List.of("measure")),
//            new ColumnConfig("gui.header.seller", List.of("seller", "name")),
//            new ColumnConfig("gui.header.createdAt", List.of("createdAt"))
//    );
//
//    @Autowired
//    private Service<Long, Payment, Predicate, Result<List<Payment>>> paymentService;
//    @Autowired
//    private Service<Long, Seller, Predicate, Result<List<Seller>>> sellerService;
//    @Autowired
//    private Service<Long, Product, Predicate, Result<List<Product>>> productService;
//
//    @Override
//    protected Result<?> updateListImpl() {
//        Result<List<Payment>> result = paymentService.loader().all();
//        if (result.isSuccess()){
//            grid.setItems(result.getValue());
//        }
//        return result;
//    }
//
//    @Override
//    protected void configureGrid() {
//        grid = new Grid<>(Payment.class);
//        grid.addClassName("payment-grid");
//        grid.setSizeFull();
//        configureGridColumns(COLUMN_CONFIGS);
//        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
//    }
//
//    @Override
//    protected void configureForm() {
//        form = new PaymentFormOld(
//                productService.loader().all().getValue(),
//                sellerService.loader().all().getValue()
//        );
//        form.setWidth("25em");
//
//        form.addListener(PaymentFormOld.PaymentSaveFormEvent.class, this::handleSavingEvent);
//        form.addListener(PaymentFormOld.PaymentDeleteFormEvent.class, this::handleDeletingEvent);
//        form.addListener(PaymentFormOld.PaymentCloseFormEvent.class, e -> closeEditor(true));
//    }
//
//    @Override
//    protected Result<List<Payment>> delete(Payment domain) {
//        return paymentService.deleter().byId(domain.getId());
//    }
//
//    @Override
//    protected Result<List<Payment>> save(Payment domain) {
//        return paymentService.saver().save(domain);
//    }
//}
