package kpn.financecontroller.gui.view;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domain.Country;
import kpn.financecontroller.gui.MainLayout;
import kpn.financecontroller.gui.event.country.view.CountryViewNotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@Scope("prototype")
@Route(value = "country", layout = MainLayout.class)
@PermitAll
public final class CountryView extends GridView<Country> {
    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
            new ColumnConfig("gui.header.id", List.of("id")),
            new ColumnConfig("gui.header.name", List.of("name"))
    );

    @Override
    protected Grid<Country> createGrid() {
        Grid<Country> grid = new Grid<>(Country.class);
        grid.addClassName("country-grid");
        return grid;
    }

    @Override
    protected ComponentEvent<?> createNotificationEvent(String text) {
        return new CountryViewNotificationEvent(this, text, NotificationType.ERROR);
    }

    @Override
    protected Country createDomain() {
        return new Country();
    }

    @Override
    protected List<ColumnConfig> getConfigList() {
        return COLUMN_CONFIGS;
    }
}
