package kpn.financecontroller.gui.view.geo.seller;

import com.querydsl.core.types.Predicate;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.data.domains.seller.Seller;
import kpn.financecontroller.gui.view.GridViewOld;
import kpn.financecontroller.gui.MainLayout;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@Scope("prototype")
@Route(value = "seller", layout = MainLayout.class)
@PermitAll
final public class SellerViewOld extends GridViewOld<Seller> {
    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
            new ColumnConfig("gui.header.id", List.of("id")),
            new ColumnConfig("gui.header.name", List.of("name")),
            new ColumnConfig("gui.header.url", List.of("url")),
            new ColumnConfig("gui.header.description", List.of("description")),
            new ColumnConfig("gui.header.address", List.of("address", "name"))
    );

    @Autowired
    private Service<Long, Seller, Predicate, Result<List<Seller>>> sellerService;
    @Autowired
    private Service<Long, Address, Predicate, Result<List<Address>>> addressService;

    @Override
    protected Result<?> updateListImpl() {
        Result<List<Seller>> result = sellerService.loader().all();
        if (result.isSuccess()){
            grid.setItems(result.getValue());
        }
        return result;
    }

    @Override
    protected void configureGrid() {
        grid = new Grid<>(Seller.class);
        grid.addClassName("address-grid");
        grid.setSizeFull();
        configureGridColumns(COLUMN_CONFIGS);
        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
    }

    @Override
    protected void configureForm() {
        form = new SellerFormOld(addressService.loader().all().getValue());
        form.setWidth("25em");

        form.addListener(SellerFormOld.SellerSaveFormEvent.class, this::handleSavingEvent);
        form.addListener(SellerFormOld.SellerDeleteFormEvent.class, this::handleDeletingEvent);
        form.addListener(SellerFormOld.SellerCloseFormEvent.class, e -> closeEditor(true));
    }

    @Override
    protected Result<List<Seller>> delete(Seller domain) {
        return sellerService.deleter().byId(domain.getId());
    }

    @Override
    protected Result<List<Seller>> save(Seller domain) {
        return sellerService.saver().save(domain);
    }
}
