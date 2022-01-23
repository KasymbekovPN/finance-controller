package kpn.financecontroller.gui.views.payment;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.building.Building;
import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.entities.building.BuildingEntity;
import kpn.financecontroller.data.entities.payment.PaymentEntity;
import kpn.financecontroller.data.entities.product.ProductEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.notifications.NotificationFactory;
import kpn.financecontroller.gui.views.EditForm;
import kpn.financecontroller.gui.views.GridView;
import kpn.financecontroller.gui.views.MainLayout;
import kpn.financecontroller.i18n.I18nService;
import kpn.financecontroller.message.LocaledMessageSeedFactory;
import kpn.financecontroller.result.Result;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@PageTitle("Payment")
@org.springframework.stereotype.Component
@Scope("prototype")
@Route(value = "", layout = MainLayout.class)
@PermitAll
public class PaymentView extends GridView<Payment> {

    private final DTOService<Payment, PaymentEntity, Long> paymentService;
    private final DTOService<Building, BuildingEntity, Long> buildingService;
    private final DTOService<Product, ProductEntity, Long> productService;

    public PaymentView(LocaledMessageSeedFactory seedFactory,
                       I18nService i18nService,
                       NotificationFactory notificationFactory,
                       DTOService<Payment, PaymentEntity, Long> paymentService,
                       DTOService<Building, BuildingEntity, Long> buildingService,
                       DTOService<Product, ProductEntity, Long> productService) {
        super(new Grid<>(Payment.class), seedFactory, i18nService, notificationFactory);
        this.paymentService = paymentService;
        this.buildingService = buildingService;
        this.productService = productService;
    }

    @Override
    protected Result<?> updateListImpl() {
        Result<List<Payment>> result = paymentService.loader().all();
        if (result.getSuccess()){
            grid.setItems(result.getValue());
        }
        return result;
    }

    @Override
    protected void configureGrid() {
        grid.addClassName("payment-grid");
        grid.setSizeFull();
        grid.setColumns("id");
        grid.addColumn(p -> p.getProduct().getName()).setHeader("Product");
        grid.addColumn(Payment::getPrice).setHeader("Price");
        grid.addColumn(p -> p.getCurrency().name()).setHeader("Currency");
        grid.addColumn(Payment::getAmount).setHeader("Amount");
        grid.addColumn(p -> p.getMeasure().name()).setHeader("Measure");
        grid.addColumn(p -> p.getBuilding().getFullName()).setHeader("Building");
        grid.addColumn(Payment::getCreatedAt).setHeader("Created at");

        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
    }

    @Override
    protected void configureForm() {
        form = new PaymentForm(
                productService.loader().all().getValue(),
                buildingService.loader().all().getValue()
        );
        form.setWidth("25em");

        form.addListener(PaymentForm.PaymentSaveFormEvent.class, this::saveContact);
        form.addListener(PaymentForm.PaymentDeleteFormEvent.class, this::deleteEvent);
        form.addListener(PaymentForm.PaymentCloseFormEvent.class, e -> closeEditor());
    }

    @Override
    protected void add() {
        grid.asSingleSelect().clear();
        editValue(new Payment());
    }

    @Override
    protected Result<Void> handleDeleteEvent(DeleteFormEvent<EditForm<Payment>, Payment> event) {
        return paymentService.deleter().byId(event.getValue().getId());
    }

    @Override
    protected Result<Payment> handleSaveEvent(SaveFormEvent<EditForm<Payment>, Payment> event) {
        return paymentService.saver().save(new PaymentEntity(event.getValue()));
    }
}
