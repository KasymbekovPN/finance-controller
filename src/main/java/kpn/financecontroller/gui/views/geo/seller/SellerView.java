// TODO: 17.07.2022 restore
//package kpn.financecontroller.gui.views.geo.seller;
//
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.router.Route;
//import kpn.financecontroller.data.domains.address.Address;
//import kpn.financecontroller.data.domains.seller.Seller;
//import kpn.financecontroller.data.entities.address.AddressEntity;
//import kpn.financecontroller.data.entities.seller.SellerEntity;
//import kpn.financecontroller.data.services.dto.DTOServiceOLdOld;
//import kpn.financecontroller.gui.views.GridView;
//import kpn.financecontroller.gui.views.MainLayout;
//import kpn.lib.result.Result;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Scope;
//
//import javax.annotation.security.PermitAll;
//import java.util.List;
//
//@Scope("prototype")
//@Route(value = "seller", layout = MainLayout.class)
//@PermitAll
//final public class SellerView extends GridView<Seller> {
//    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
//            new ColumnConfig("gui.header.id", List.of("id")),
//            new ColumnConfig("gui.header.name", List.of("name")),
//            new ColumnConfig("gui.header.url", List.of("url")),
//            new ColumnConfig("gui.header.description", List.of("description")),
//            new ColumnConfig("gui.header.address", List.of("address"))
//    );
//
//    @Autowired
//    private DTOServiceOLdOld<Seller, SellerEntity> sellerService;
//    @Autowired
//    private DTOServiceOLdOld<Address, AddressEntity> addressService;
//
//    @Override
//    protected Result<?> updateListImpl() {
//        Result<List<Seller>> result = sellerService.loader().all();
//        if (result.isSuccess()){
//            grid.setItems(result.getValue());
//        }
//        return result;
//    }
//
//    @Override
//    protected void configureGrid() {
//        grid = new Grid<>(Seller.class);
//        grid.addClassName("address-grid");
//        grid.setSizeFull();
//        configureGridColumns(COLUMN_CONFIGS);
//        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
//    }
//
//    @Override
//    protected void configureForm() {
//        form = new SellerForm(addressService.loader().all().getValue());
//        form.setWidth("25em");
//
//        form.addListener(SellerForm.SellerSaveFormEvent.class, this::handleSavingEvent);
//        form.addListener(SellerForm.SellerDeleteFormEvent.class, this::handleDeletingEvent);
//        form.addListener(SellerForm.SellerCloseFormEvent.class, e -> closeEditor(true));
//    }
//
//    @Override
//    protected Result<Void> delete(Seller domain) {
//        return sellerService.deleter().byId(domain.getId());
//    }
//
//    @Override
//    protected Result<Seller> save(Seller domain) {
//        return sellerService.saver().save(new SellerEntity(domain));
//    }
//}
