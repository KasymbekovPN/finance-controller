package kpn.financecontroller.gui.view.tag;

import com.querydsl.core.types.Predicate;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.gui.view.GridViewOld;
import kpn.financecontroller.gui.MainLayout;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@Scope("prototype")
@Route(value = "tagold", layout = MainLayout.class)
@PermitAll
final public class TagViewOldOld extends GridViewOld<Tag> {
    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
            new ColumnConfig("gui.header.id", List.of("id")),
            new ColumnConfig("gui.header.name", List.of("name"))
    );

    @Autowired
    private Service<Long, Tag, Predicate, Result<List<Tag>>> tagService;

    @Override
    protected Result<?> updateListImpl() {
        Result<List<Tag>> result = tagService.loader().all();
        if (result.isSuccess()){
            grid.setItems(result.getValue());
        }
        return result;
    }

    @Override
    protected void configureGrid() {
        grid = new Grid<>(Tag.class);
        grid.addClassName("tag-grid");
        grid.setSizeFull();
        configureGridColumns(COLUMN_CONFIGS);
        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
    }

    @Override
    protected void configureForm() {
        form = new TagFormOldOld();
        form.setWidth("25em");

        form.addListener(TagFormOldOld.TagSaveFormEvent.class, this::handleSavingEvent);
        form.addListener(TagFormOldOld.TagDeleteFormEvent.class, this::handleDeletingEvent);
        form.addListener(TagFormOldOld.TagCloseFormEvent.class, e -> closeEditor(true));
    }

    @Override
    protected Result<List<Tag>> delete(Tag domain) {
        return tagService.deleter().byId(domain.getId());
    }

    @Override
    protected Result<List<Tag>> save(Tag domain) {
        return tagService.saver().save(domain);
    }
}
