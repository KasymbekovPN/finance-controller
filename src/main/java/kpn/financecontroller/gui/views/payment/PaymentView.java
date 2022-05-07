package kpn.financecontroller.gui.views.payment;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.data.domains.place.Place;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.entities.payment.PaymentEntity;
import kpn.financecontroller.data.entities.place.PlaceEntity;
import kpn.financecontroller.data.entities.product.ProductEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.views.EditForm;
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

    @Autowired
    private DTOService<Payment, PaymentEntity, Long> paymentService;
    @Autowired
    private DTOService<Place, PlaceEntity, Long> placeService;
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

        grid.setColumns();
        grid.addColumn(Payment::getId).setHeader(getTranslation("gui.header.id"));
        grid.addColumn(p -> p.getProduct().getName()).setHeader(getTranslation("gui.header.product"));
        grid.addColumn(Payment::getPrice).setHeader(getTranslation("gui.header.price"));
        grid.addColumn(p -> p.getCurrency().name()).setHeader(getTranslation("gui.header.currency"));
        grid.addColumn(p -> {return p.getMeasure() != null ? p.getMeasure().name() : "-";}).setHeader(getTranslation("gui.header.amount"));
        grid.addColumn(p -> {return p.getAmount() != null ? p.getAmount() : "-";}).setHeader(getTranslation("gui.header.measure"));
        grid.addColumn(p -> {return p.getPlace() != null ? p.getPlace().getName() : "-";}).setHeader(getTranslation("gui.header.place"));
        grid.addColumn(Payment::getCreatedAt).setHeader(getTranslation("gui.header.createdAt"));

        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
    }

    @Override
    protected void configureForm() {
        form = new PaymentForm(
                productService.loader().all().getValue(),
                placeService.loader().all().getValue()
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
