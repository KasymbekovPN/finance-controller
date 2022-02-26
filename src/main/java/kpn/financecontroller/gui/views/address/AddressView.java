package kpn.financecontroller.gui.views.address;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.entities.address.AddressEntity;
import kpn.financecontroller.data.entities.street.StreetEntity;
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

@org.springframework.stereotype.Component
@Scope("prototype")
@Route(value = "address", layout = MainLayout.class)
@PermitAll
public class AddressView extends GridView<Address> {

    private final DTOService<Address, AddressEntity, Long> addressService;
    private final DTOService<Street, StreetEntity, Long> streetService;

    public AddressView(LocaledMessageSeedFactory seedFactory,
                       I18nService i18nService,
                       NotificationFactory notificationFactory,
                       DTOService<Address, AddressEntity, Long> addressService,
                       DTOService<Street, StreetEntity, Long> streetService) {
        super(new Grid<>(Address.class), seedFactory, i18nService, notificationFactory, "gui.addresses");
        this.addressService = addressService;
        this.streetService = streetService;
    }

    @Override
    protected Result<?> updateListImpl() {
        Result<List<Address>> result = addressService.loader().all();
        if (result.getSuccess()){
            grid.setItems(result.getValue());
        }
        return result;
    }

    @Override
    protected void configureGrid() {
        grid.addClassName("address-grid");
        grid.setSizeFull();

        grid.setColumns();
        grid.addColumn(Address::getId).setHeader(getTranslation("gui.id"));
        grid.addColumn(Address::getName).setHeader(getTranslation("gui.name"));
        grid.addColumn(b -> b.getStreet().getName()).setHeader(getTranslation("gui.street"));
        grid.addColumn(b -> b.getStreet().getCity().getName()).setHeader(getTranslation("gui.city"));
        grid.addColumn(b -> b.getStreet().getCity().getRegion().getName()).setHeader(getTranslation("gui.region"));
        grid.addColumn(b -> b.getStreet().getCity().getRegion().getCountry().getName()).setHeader(getTranslation("gui.country"));

        grid.getColumns().forEach(column -> column.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
    }

    @Override
    protected void configureForm() {
        form = new AddressForm(streetService.loader().all().getValue());
        form.setWidth("25em");

        form.addListener(AddressForm.AddressSaveFormEvent.class, this::saveContact);
        form.addListener(AddressForm.AddressDeleteFormEvent.class, this::deleteEvent);
        form.addListener(AddressForm.AddressCloseFormEvent.class, e -> closeEditor());
    }

    @Override
    protected void add() {
        grid.asSingleSelect().clear();
        editValue(new Address());
    }

    @Override
    protected Result<Void> handleDeleteEvent(DeleteFormEvent<EditForm<Address>, Address> event) {
        return addressService.deleter().byId(event.getValue().getId());
    }

    @Override
    protected Result<Address> handleSaveEvent(SaveFormEvent<EditForm<Address>, Address> event) {
        return addressService.saver().save(new AddressEntity(event.getValue()));
    }
}
