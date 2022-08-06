package kpn.financecontroller.gui.view;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.gui.MainLayout;
import kpn.financecontroller.gui.event.product.view.ProductViewNotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@Scope("prototype")
@Route(value = "product", layout = MainLayout.class)
@PermitAll
public final class ProductView extends GridView<Product> {
    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
            new ColumnConfig("gui.header.id", List.of("id")),
            new ColumnConfig("gui.header.name", List.of("name")),
            new ColumnConfig("gui.header.tags", List.of("tags"))
    );

    @Override
    protected Grid<Product> createGrid() {
        Grid<Product> grid = new Grid<>(Product.class);
        grid.addClassName("product-grid");
        return grid;
    }

    @Override
    protected ComponentEvent<?> createNotificationEvent(String text) {
        return new ProductViewNotificationEvent(this, text, NotificationType.ERROR);
    }

    @Override
    protected Product createDomain() {
        return new Product();
    }

    @Override
    protected List<ColumnConfig> getConfigList() {
        return COLUMN_CONFIGS;
    }
}
