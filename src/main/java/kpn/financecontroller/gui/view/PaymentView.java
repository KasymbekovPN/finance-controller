package kpn.financecontroller.gui.view;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.gui.MainLayout;
import kpn.financecontroller.gui.event.payment.view.PaymentViewNotificationEvent;
import kpn.financecontroller.gui.notifications.Notifications;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@Scope("prototype")
@Route(value = "payment", layout = MainLayout.class)
@PermitAll
public final class PaymentView extends GridView<Payment> {
    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
            new ColumnConfig("gui.header.id", List.of("id")),
            new ColumnConfig("gui.header.product", List.of("product", "name")),
            new ColumnConfig("gui.header.price", List.of("price")),
            new ColumnConfig("gui.header.currency", List.of("currency")),
            new ColumnConfig("gui.header.amount", List.of("amount")),
            new ColumnConfig("gui.header.measure", List.of("measure")),
            new ColumnConfig("gui.header.seller", List.of("seller", "name")),
            new ColumnConfig("gui.header.createdAt", List.of("createdAt"))
    );

    @Override
    protected Grid<Payment> createGrid() {
        Grid<Payment> grid = new Grid<>(Payment.class);
        grid.addClassName("payment-grid");
        return grid;
    }

    @Override
    protected ComponentEvent<?> createNotificationEvent(String text) {
        return new PaymentViewNotificationEvent(this, text, Notifications.ERROR);
    }

    @Override
    protected Payment createDomain() {
        return new Payment();
    }

    @Override
    protected List<ColumnConfig> getConfigList() {
        return COLUMN_CONFIGS;
    }
}
