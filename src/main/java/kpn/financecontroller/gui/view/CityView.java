package kpn.financecontroller.gui.view;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.gui.MainLayout;
import kpn.financecontroller.gui.event.city.view.CityViewNotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@Scope("prototype")
@Route(value = "city", layout = MainLayout.class)
@PermitAll
public final class CityView extends GridView<City> {
    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
            new ColumnConfig("gui.header.id", List.of("id")),
            new ColumnConfig("gui.header.name", List.of("name")),
            new ColumnConfig("gui.header.region", List.of("region", "name")),
            new ColumnConfig("gui.header.country", List.of("region", "country", "name"))
    );

    @Override
    protected Grid<City> createGrid() {
        Grid<City> grid = new Grid<>(City.class);
        grid.addClassName("city-grid");
        return grid;
    }

    @Override
    protected ComponentEvent<?> createNotificationEvent(String text) {
        return new CityViewNotificationEvent(this, text, NotificationType.ERROR);
    }

    @Override
    protected City createDomain() {
        return new City();
    }

    @Override
    protected List<ColumnConfig> getConfigList() {
        return COLUMN_CONFIGS;
    }
}
