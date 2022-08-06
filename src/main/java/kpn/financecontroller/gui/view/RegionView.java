package kpn.financecontroller.gui.view;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.gui.MainLayout;
import kpn.financecontroller.gui.event.region.view.RegionViewNotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@Scope("prototype")
@Route(value = "region", layout = MainLayout.class)
@PermitAll
public final class RegionView extends GridView<Region> {
    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
            new ColumnConfig("gui.header.id", List.of("id")),
            new ColumnConfig("gui.header.name", List.of("name")),
            new ColumnConfig("gui.header.country", List.of("country", "name"))
    );

    @Override
    protected Grid<Region> createGrid() {
        Grid<Region> grid = new Grid<>(Region.class);
        grid.addClassName("region-grid");
        return grid;
    }

    @Override
    protected ComponentEvent<?> createNotificationEvent(String text) {
        return new RegionViewNotificationEvent(this, text, NotificationType.ERROR);
    }

    @Override
    protected Region createDomain() {
        return new Region();
    }

    @Override
    protected List<ColumnConfig> getConfigList() {
        return COLUMN_CONFIGS;
    }
}
