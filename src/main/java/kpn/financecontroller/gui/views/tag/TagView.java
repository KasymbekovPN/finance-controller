package kpn.financecontroller.gui.views.tag;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.notifications.NotificationFactory;
import kpn.financecontroller.gui.views.EditForm;
import kpn.financecontroller.gui.views.GridView;
import kpn.financecontroller.gui.views.MainLayout;
import kpn.financecontroller.i18n.I18nService;
import kpn.financecontroller.message.LocaledMessageSeedFactory;
import kpn.financecontroller.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@PageTitle("Tag")
@org.springframework.stereotype.Component
@Scope("prototype")
@Route(value = "tag", layout = MainLayout.class)
@PermitAll
public class TagView extends GridView<Tag> {

    private final DTOService<Tag, TagEntity, Long> tagService;

    @Autowired
    public TagView(LocaledMessageSeedFactory seedFactory,
                   I18nService i18nService,
                   NotificationFactory notificationFactory,
                   DTOService<Tag, TagEntity, Long> tagService) {
        super(new Grid<>(Tag.class), seedFactory, i18nService, notificationFactory);
        this.tagService = tagService;
    }

    @Override
    protected Result<?> updateListImpl() {
        Result<List<Tag>> result = tagService.loader().all();
        if (result.getSuccess()){
            grid.setItems(result.getValue());
        }
        return result;
    }

    @Override
    protected void configureGrid() {
        grid.addClassName("tag-grid");
        grid.setSizeFull();
        grid.setColumns("id", "name");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
    }

    @Override
    protected void configureForm() {
        form = new TagForm();
        form.setWidth("25em");

        form.addListener(TagForm.TagSaveFormEvent.class, this::saveContact);
        form.addListener(TagForm.TagDeleteFormEvent.class, this::deleteEvent);
        form.addListener(TagForm.TagCloseFormEvent.class, e -> closeEditor());
    }

    @Override
    protected void add() {
        grid.asSingleSelect().clear();
        editValue(new Tag());
    }

    @Override
    protected Result<Void> handleDeleteEvent(DeleteFormEvent<EditForm<Tag>, Tag> event) {
        return tagService.deleter().byId(event.getValue().getId());
    }

    @Override
    protected Result<Tag> handleSaveEvent(SaveFormEvent<EditForm<Tag>, Tag> event) {
        return tagService.saver().save(new TagEntity(event.getValue()));
    }
}
