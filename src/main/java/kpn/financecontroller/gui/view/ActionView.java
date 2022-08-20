package kpn.financecontroller.gui.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domain.Action;
import kpn.financecontroller.gui.MainLayout;
import kpn.financecontroller.gui.display.ActionDisplay;
import kpn.financecontroller.gui.event.CancelEvent;
import kpn.financecontroller.gui.event.action.view.ActionViewNotificationEvent;
import kpn.financecontroller.gui.form.Form;
import kpn.financecontroller.gui.notifications.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ActionDisplay display;

    @Override
    protected void configureGrid() {
        grid = createGrid();
//        grid.setSizeFull(); // TODO: 15.08.2022 ???
        grid.setWidth(49, Unit.PERCENTAGE);
        grid.setHeight(100, Unit.PERCENTAGE);
        configureGridColumns(getConfigList());
        // TODO: 20.08.2022 del
//        grid.asSingleSelect().addValueChangeListener(e -> editFormValue(e.getValue()));
        grid.asSingleSelect().addValueChangeListener(e -> {
           editFormValue(e.getValue());
           changeDisplayVisibility(e.getValue());
        });
    }

    @Override
    protected Grid<Action> createGrid() {
        Grid<Action> grid = new Grid<>(Action.class);
        grid.addClassName("action-grid");

        return grid;
    }

    @Override
    protected ComponentEvent<?> createNotificationEvent(String text) {
        return new ActionViewNotificationEvent(this, text, NotificationType.ERROR);
    }

    @Override
    protected Action createDomain() {
        return new Action();
    }

    @Override
    protected List<ColumnConfig> getConfigList() {
        return COLUMN_CONFIGS;
    }

    @Override
    protected Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form, display);
        content.addClassName("content");
        // TODO: 15.08.2022 ???
//        content.setFlexGrow(1, grid);
//        content.setFlexGrow(1, form);
        content.setSizeFull();
        return content;
    }

    @Override
    protected void processAddButtonClick() {
        super.processAddButtonClick();
        display.setVisible(true);
    }

    @Override
    public <F extends Form<Action>> void handleCancelEvent(CancelEvent<F, Action> event) {
        super.handleCancelEvent(event);
        display.setVisible(false);
    }

    private void changeDisplayVisibility(Action value) {
        display.setVisible(value != null);
    }
}
