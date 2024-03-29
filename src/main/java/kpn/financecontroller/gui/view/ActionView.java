package kpn.financecontroller.gui.view;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domain.Action;
import kpn.financecontroller.gui.MainLayout;
import kpn.financecontroller.gui.event.action.view.ActionViewNotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.lib.seed.ImmutableSeed;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@Scope("prototype")
@Route(value = "action", layout = MainLayout.class)
@PermitAll
public final class ActionView extends GridView<Action> {
    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
            new ColumnConfig("gui.header.id", List.of("id")),
            new ColumnConfig("gui.header.description", List.of("description"))
    );

    @Override
    protected void configureGrid() {
        grid = createGrid();
        grid.setWidth(49, Unit.PERCENTAGE);
        grid.setHeight(100, Unit.PERCENTAGE);
        configureGridColumns(getConfigList());
        grid.asSingleSelect().addValueChangeListener(e -> {editFormValue(e.getValue());});
    }

    @Override
    protected Grid<Action> createGrid() {
        Grid<Action> grid = new Grid<>(Action.class);
        grid.addClassName("action-grid");

        return grid;
    }

    @Override
    protected ComponentEvent<?> createNotificationEvent(String text) {
        return new ActionViewNotificationEvent(
                this,
                ImmutableSeed.builder().code(text).build(),
                NotificationType.ERROR
        );
    }

    @Override
    protected Action createDomain() {
        return new Action();
    }

    @Override
    protected List<ColumnConfig> getConfigList() {
        return COLUMN_CONFIGS;
    }
}
