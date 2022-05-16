package kpn.financecontroller.gui.views.payment;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.data.domains.seller.Seller;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.entities.payment.PaymentEntity;
import kpn.financecontroller.data.entities.seller.SellerEntity;
import kpn.financecontroller.data.entities.product.ProductEntity;
import kpn.financecontroller.data.services.dto.DTOService;
import kpn.financecontroller.gui.views.GridView;
import kpn.financecontroller.gui.views.MainLayout;
import kpn.lib.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@Scope("prototype")
@Route(value = "", layout = MainLayout.class)
@PermitAll
final public class PaymentView extends GridView<Payment> {
    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
            new ColumnConfig("gui.header.id", List.of("id")),
            new ColumnConfig("gui.header.product", List.of("product", "name")),
            new ColumnConfig("gui.header.price", List.of("price")),
            new ColumnConfig("gui.header.currency", List.of("currency")),
            new ColumnConfig("gui.header.amount", List.of("amount")),
            new ColumnConfig("gui.header.measure", List.of("measure")),
            new ColumnConfig("gui.header.seller", List.of("seller", "name")),
            new ColumnConfig("gui.header.createdAt", List.of("createdAt"))
    );

    @Autowired
    private DTOService<Payment, PaymentEntity, Long> paymentService;
    @Autowired
    private DTOService<Seller, SellerEntity, Long> sellerService;
    @Autowired
    private DTOService<Product, ProductEntity, Long> productService;

    @Override
    protected Result<?> updateListImpl() {
        Result<List<Payment>> result = paymentService.loader().all();
        if (result.isSuccess()){
            grid.setItems(result.getValue());
        }
        return result;
    }

    @Override
    protected void configureGrid() {
        grid = new Grid<>(Payment.class);
        grid.addClassName("payment-grid");
        grid.setSizeFull();
        configureGridColumns(COLUMN_CONFIGS);
        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
    }

    @Override
    protected void configureForm() {
        form = new PaymentForm(
                productService.loader().all().getValue(),
                sellerService.loader().all().getValue()
        );
        form.setWidth("25em");

        form.addListener(PaymentForm.PaymentSaveFormEvent.class, this::handleSavingEvent);
        form.addListener(PaymentForm.PaymentDeleteFormEvent.class, this::handleDeletingEvent);
        form.addListener(PaymentForm.PaymentCloseFormEvent.class, e -> closeEditor());
    }

    @Override
    protected void add() {
        grid.asSingleSelect().clear();
        editValue(new Payment());
    }

    @Override
    protected Result<Void> delete(Payment domain) {
        return paymentService.deleter().byId(domain.getId());
    }

    @Override
    protected Result<Payment> save(Payment domain) {
        return paymentService.saver().save(new PaymentEntity(domain));
    }
}
