package kpn.financecontroller.gui.views.tag;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.tag.Tag;
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
@Route(value = "tag", layout = MainLayout.class)
@PermitAll
final public class TagView extends GridView<Tag> {
    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
            new ColumnConfig("gui.header.id", List.of("id")),
            new ColumnConfig("gui.header.name", List.of("name"))
    );

    @Autowired
    private DTOService<Tag, TagEntity, Long> tagService;

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
        form = new TagForm();
        form.setWidth("25em");

        form.addListener(TagForm.TagSaveFormEvent.class, this::handleSavingEvent);
        form.addListener(TagForm.TagDeleteFormEvent.class, this::handleDeletingEvent);
        form.addListener(TagForm.TagCloseFormEvent.class, e -> closeEditor(true));
    }

    @Override
    protected Result<Void> delete(Tag domain) {
        return tagService.deleter().byId(domain.getId());
    }

    @Override
    protected Result<Tag> save(Tag domain) {
        return tagService.saver().save(new TagEntity(domain));
    }
}
