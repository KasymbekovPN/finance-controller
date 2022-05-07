package kpn.financecontroller.gui.views.tag;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.views.EditForm;
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

        grid.setColumns();
        grid.addColumn(Tag::getId).setHeader(getTranslation("gui.header.id"));
        grid.addColumn(Tag::getInfo).setHeader(getTranslation("gui.header.name"));
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
    }

    @Override
    protected void configureForm() {
        form = new TagForm();
        form.setWidth("25em");

        form.addListener(TagForm.TagSaveFormEvent.class, this::handleSavingEvent);
        form.addListener(TagForm.TagDeleteFormEvent.class, this::handleDeletingEvent);
        form.addListener(TagForm.TagCloseFormEvent.class, e -> closeEditor());
    }

    @Override
    protected void add() {
        grid.asSingleSelect().clear();
        editValue(new Tag());
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
