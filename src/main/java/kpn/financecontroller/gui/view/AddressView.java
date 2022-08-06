package kpn.financecontroller.gui.view;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.gui.MainLayout;
import kpn.financecontroller.gui.event.address.view.AddressViewNotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@Scope("prototype")
@Route(value = "address", layout = MainLayout.class)
@PermitAll
public final class AddressView extends GridView<Address>{
    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
            new ColumnConfig("gui.header.id", List.of("id")),
            new ColumnConfig("gui.header.name", List.of("name")),
            new ColumnConfig("gui.header.street", List.of("street", "name")),
            new ColumnConfig("gui.header.city", List.of("street", "city", "name")),
            new ColumnConfig("gui.header.region", List.of("street", "city", "region", "name")),
            new ColumnConfig("gui.header.country", List.of("street", "city", "region", "country", "name"))
    );

    @Override
    protected Grid<Address> createGrid() {
        Grid<Address> grid = new Grid<>(Address.class);
        grid.addClassName("address-grid");
        return grid;
    }

    @Override
    protected ComponentEvent<?> createNotificationEvent(String text) {
        return new AddressViewNotificationEvent(this, text, NotificationType.ERROR);
    }

    @Override
    protected Address createDomain() {
        return new Address();
    }

    @Override
    protected List<ColumnConfig> getConfigList() {
        return COLUMN_CONFIGS;
    }
}
