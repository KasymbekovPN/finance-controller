package kpn.financecontroller.gui.view;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domain.Street;
import kpn.financecontroller.gui.MainLayout;
import kpn.financecontroller.gui.event.street.view.StreetViewNotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@Scope("prototype")
@Route(value = "street", layout = MainLayout.class)
@PermitAll
public final class StreetView extends GridView<Street> {
    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
            new ColumnConfig("gui.header.id", List.of("id")),
            new ColumnConfig("gui.header.name", List.of("name")),
            new ColumnConfig("gui.header.city", List.of("city", "name")),
            new ColumnConfig("gui.header.region", List.of("city", "region", "name")),
            new ColumnConfig("gui.header.country", List.of("city", "region", "country", "name"))
    );

    @Override
    protected Grid<Street> createGrid() {
        Grid<Street> grid = new Grid<>(Street.class);
        grid.addClassName("street-grid");
        return grid;
    }

    @Override
    protected ComponentEvent<?> createNotificationEvent(String text) {
        return new StreetViewNotificationEvent(this, text, NotificationType.ERROR);
    }

    @Override
    protected Street createDomain() {
        return new Street();
    }

    @Override
    protected List<ColumnConfig> getConfigList() {
        return COLUMN_CONFIGS;
    }
}
