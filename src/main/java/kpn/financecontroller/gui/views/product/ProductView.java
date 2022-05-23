package kpn.financecontroller.gui.views.product;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.data.entities.product.ProductEntity;
import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.data.services.dto.DTOService;
import kpn.financecontroller.gui.views.GridView;
import kpn.financecontroller.gui.views.MainLayout;
import kpn.lib.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@Scope("prototype")
@Route(value = "product", layout = MainLayout.class)
@PermitAll
final public class ProductView extends GridView<Product> {
    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
            new ColumnConfig("gui.header.id", List.of("id")),
            new ColumnConfig("gui.header.name", List.of("name")),
            new ColumnConfig("gui.header.tags", List.of("tags"))
    );

    @Autowired
    private DTOService<Product, ProductEntity, Long> service;
    @Autowired
    private DTOService<Tag, TagEntity, Long> tagService;

    @Override
    protected Result<?> updateListImpl() {
        Result<List<Product>> result = service.loader().all();
        if (result.isSuccess()){
            grid.setItems(result.getValue());
        }
        return result;
    }

    @Override
    protected void configureGrid() {
        grid = new Grid<>(Product.class);
        grid.addClassName("product-grid");
        grid.setSizeFull();
        configureGridColumns(COLUMN_CONFIGS);
        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
    }

    @Override
    protected void configureForm() {
        form = new ProductForm(tagService.loader().all().getValue());
        form.setWidth("25em");

        form.addListener(ProductForm.ProductSaveFormEvent.class, this::handleSavingEvent);
        form.addListener(ProductForm.ProductDeleteFormEvent.class, this::handleDeletingEvent);
        form.addListener(ProductForm.ProductCloseFormEvent.class, e -> closeEditor(true));
    }

    @Override
    protected Result<Void> delete(Product domain) {
        return service.deleter().byId(domain.getId());
    }

    @Override
    protected Result<Product> save(Product domain) {
        return service.saver().save(new ProductEntity(domain));
    }
}
