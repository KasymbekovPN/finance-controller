// TODO: 04.08.2022 del
//package kpn.financecontroller.gui.view.product;
//
//import com.querydsl.core.types.Predicate;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.router.Route;
//import kpn.financecontroller.data.domains.product.Product;
//import kpn.financecontroller.data.domains.tag.Tag;
//import kpn.financecontroller.gui.view.GridViewOld;
//import kpn.financecontroller.gui.MainLayout;
//import kpn.lib.result.Result;
//import kpn.lib.service.Service;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Scope;
//
//import javax.annotation.security.PermitAll;
//import java.util.List;
//
//// TODO: 04.08.2022 del
//@Scope("prototype")
//@Route(value = "productold", layout = MainLayout.class)
//@PermitAll
//final public class ProductViewOld extends GridViewOld<Product> {
//    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
//            new ColumnConfig("gui.header.id", List.of("id")),
//            new ColumnConfig("gui.header.name", List.of("name")),
//            new ColumnConfig("gui.header.tags", List.of("tags"))
//    );
//
//    @Autowired
//    private Service<Long, Product, Predicate, Result<List<Product>>> productService;
//    @Autowired
//    private Service<Long, Tag, Predicate, Result<List<Tag>>> tagService;
//
//    @Override
//    protected Result<?> updateListImpl() {
//        Result<List<Product>> result = productService.loader().all();
//        if (result.isSuccess()){
//            grid.setItems(result.getValue());
//        }
//        return result;
//    }
//
//    @Override
//    protected void configureGrid() {
//        grid = new Grid<>(Product.class);
//        grid.addClassName("product-grid");
//        grid.setSizeFull();
//        configureGridColumns(COLUMN_CONFIGS);
//        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
//    }
//
//    @Override
//    protected void configureForm() {
//        form = new ProductFormOld(tagService.loader().all().getValue());
//        form.setWidth("25em");
//
//        form.addListener(ProductFormOld.ProductSaveFormEvent.class, this::handleSavingEvent);
//        form.addListener(ProductFormOld.ProductDeleteFormEvent.class, this::handleDeletingEvent);
//        form.addListener(ProductFormOld.ProductCloseFormEvent.class, e -> closeEditor(true));
//    }
//
//    @Override
//    protected Result<List<Product>> delete(Product domain) {
//        return productService.deleter().byId(domain.getId());
//    }
//
//    @Override
//    protected Result<List<Product>> save(Product domain) {
//        return productService.saver().save(domain);
//    }
//}
