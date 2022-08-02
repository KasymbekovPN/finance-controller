package kpn.financecontroller.gui.view;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.seller.Seller;
import kpn.financecontroller.gui.MainLayout;
import kpn.financecontroller.gui.event.seller.view.SellerViewNotificationEvent;
import kpn.financecontroller.gui.notifications.Notifications;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@Scope("prototype")
@Route(value = "seller", layout = MainLayout.class)
@PermitAll
public final class SellerView extends GridView<Seller> {
    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
            new ColumnConfig("gui.header.id", List.of("id")),
            new ColumnConfig("gui.header.name", List.of("name")),
            new ColumnConfig("gui.header.url", List.of("url")),
            new ColumnConfig("gui.header.description", List.of("description")),
            new ColumnConfig("gui.header.address", List.of("address", "name"))
    );

    @Override
    protected Grid<Seller> createGrid() {
        Grid<Seller> grid = new Grid<>(Seller.class);
        grid.addClassName("address-grid");
        return grid;
    }

    @Override
    protected ComponentEvent<?> createNotificationEvent(String text) {
        return new SellerViewNotificationEvent(this, text, Notifications.ERROR);
    }

    @Override
    protected Seller createDomain() {
        return new Seller();
    }

    @Override
    protected List<ColumnConfig> getConfigList() {
        return COLUMN_CONFIGS;
    }
}
