package kpn.financecontroller.gui.view;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domain.Tag;
import kpn.financecontroller.gui.MainLayout;
import kpn.financecontroller.gui.event.tag.view.TagViewNotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.lib.seed.ImmutableSeed;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@Scope("prototype")
@Route(value = "tag", layout = MainLayout.class)
@PermitAll
public final class TagView extends GridView<Tag> {

    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
            new ColumnConfig("gui.header.id", List.of("id")),
            new ColumnConfig("gui.header.name", List.of("name"))
    );

    @Override
    protected Grid<Tag> createGrid() {
        Grid<Tag> grid = new Grid<>(Tag.class);
        grid.addClassName("tag-grid");
        return grid;
    }

    @Override
    protected ComponentEvent<?> createNotificationEvent(String text) {
        return new TagViewNotificationEvent(
                this,
                ImmutableSeed.builder().code(text).build(),
                NotificationType.ERROR
        );
    }

    @Override
    protected Tag createDomain() {
        return new Tag();
    }

    @Override
    protected List<ColumnConfig> getConfigList() {
        return COLUMN_CONFIGS;
    }
}