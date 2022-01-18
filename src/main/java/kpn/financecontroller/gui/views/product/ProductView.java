package kpn.financecontroller.gui.views.product;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.product.Product;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@PageTitle("Product")
@org.springframework.stereotype.Component
@Scope("prototype")
@Route(value = "product", layout = MainLayout.class)
@PermitAll
public class ProductView extends GridView<Product> {

    private final DTOService<Product, ProductEntity, Long> service;

    @Autowired
    public ProductView(LocaledMessageSeedFactory seedFactory,
                        I18nService i18nService,
                        NotificationFactory notificationFactory,
                        DTOService<Product, ProductEntity, Long> service) {
        super(new Grid<>(Product.class), seedFactory, i18nService, notificationFactory);
        this.service = service;
    }

    @Override
    protected Result<?> updateListImpl() {
        Result<List<Product>> result = service.loader().all();
        if (result.getSuccess()){
            grid.setItems(result.getValue());
        }
        return result;
    }

    @Override
    protected void configureGrid() {
        grid.addClassName("product-grid");
        grid.setSizeFull();
        grid.setColumns("id", "name");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
    }

    @Override
    protected void configureForm() {
        form = new ProductForm();
        form.setWidth("25em");

        form.addListener(ProductForm.ProductSaveFormEvent.class, this::saveContact);
        form.addListener(ProductForm.ProductDeleteFormEvent.class, this::deleteEvent);
        form.addListener(ProductForm.ProductCloseFormEvent.class, e -> closeEditor());
    }

    @Override
    protected void add() {
        grid.asSingleSelect().clear();
        editValue(new Product());
    }

    @Override
    protected Result<Void> handleDeleteEvent(DeleteFormEvent<EditForm<Product>, Product> event) {
        return service.deleter().byId(event.getValue().getId());
    }

    @Override
    protected Result<Product> handleSaveEvent(SaveFormEvent<EditForm<Product>, Product> event) {
        return service.saver().save(new ProductEntity(event.getValue()));
    }
}