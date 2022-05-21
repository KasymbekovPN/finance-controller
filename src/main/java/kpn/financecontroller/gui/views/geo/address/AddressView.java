package kpn.financecontroller.gui.views.geo.address;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.entities.address.AddressEntity;
import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.gui.views.GridView;
import kpn.financecontroller.gui.views.MainLayout;
import kpn.lib.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@Scope("prototype")
@Route(value = "address", layout = MainLayout.class)
@PermitAll
final public class AddressView extends GridView<Address> {
    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
            new ColumnConfig("gui.header.id", List.of("id")),
            new ColumnConfig("gui.header.name", List.of("name")),
            new ColumnConfig("gui.header.street", List.of("street", "name")),
            new ColumnConfig("gui.header.city", List.of("street", "city", "name")),
            new ColumnConfig("gui.header.region", List.of("street", "city", "region", "name")),
            new ColumnConfig("gui.header.country", List.of("street", "city", "region", "country", "name"))
    );

    @Autowired
    private DTOService<Address, AddressEntity, Long> addressService;
    @Autowired
    private DTOService<Street, StreetEntity, Long> streetService;

    @Override
    protected Result<?> updateListImpl() {
        Result<List<Address>> result = addressService.loader().all();
        if (result.isSuccess()){
            grid.setItems(result.getValue());
        }
        return result;
    }

    @Override
    protected void configureGrid() {
        grid = new Grid<>(Address.class);
        grid.addClassName("address-grid");
        grid.setSizeFull();
        configureGridColumns(COLUMN_CONFIGS);
        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
    }

    @Override
    protected void configureForm() {
        form = new AddressForm(streetService.loader().all().getValue());
        form.setWidth("25em");

        form.addListener(AddressForm.AddressSaveFormEvent.class, this::handleSavingEvent);
        form.addListener(AddressForm.AddressDeleteFormEvent.class, this::handleDeletingEvent);
        form.addListener(AddressForm.AddressCloseFormEvent.class, e -> closeEditor(true));
    }

    @Override
    protected Result<Void> delete(Address domain) {
        return addressService.deleter().byId(domain.getId());
    }

    @Override
    protected Result<Address> save(Address domain) {
        return addressService.saver().save(new AddressEntity(domain));
    }
}
