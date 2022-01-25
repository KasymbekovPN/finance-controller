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

@PageTitle("gui.payments")
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

        grid.setColumns();
        grid.addColumn(Payment::getId).setHeader(getTranslation("gui.id"));
        grid.addColumn(p -> p.getProduct().getName()).setHeader(getTranslation("gui.product"));
        grid.addColumn(Payment::getPrice).setHeader(getTranslation("gui.price"));
        grid.addColumn(p -> p.getCurrency().name()).setHeader(getTranslation("gui.currency"));
        grid.addColumn(Payment::getAmount).setHeader(getTranslation("gui.amount"));
        grid.addColumn(p -> p.getMeasure().name()).setHeader(getTranslation("gui.measure"));
        grid.addColumn(p -> p.getBuilding().getFullName()).setHeader(getTranslation("gui.building"));
        grid.addColumn(Payment::getCreatedAt).setHeader(getTranslation("gui.createdAt"));

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
