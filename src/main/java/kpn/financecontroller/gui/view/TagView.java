package kpn.financecontroller.gui.view;

import com.querydsl.core.types.Predicate;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.gui.event.CloseFormEvent;
import kpn.financecontroller.gui.event.DeleteFormEvent;
import kpn.financecontroller.gui.event.SaveFormEvent;
import kpn.financecontroller.gui.event.tag.view.TagViewNotificationEvent;
import kpn.financecontroller.gui.form.Form;
import kpn.financecontroller.gui.form.TagForm;
import kpn.financecontroller.gui.generators.ClassAliasGenerator;
import kpn.financecontroller.gui.notifications.Notifications;
import kpn.financecontroller.gui.MainLayout;
import kpn.financecontroller.gui.controller.TagViewController;
import kpn.lib.result.Result;
import kpn.lib.ripper.DefaultRipperArg;
import kpn.lib.ripper.Ripper;
import kpn.lib.service.Service;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import java.util.ArrayDeque;
import java.util.List;

@Scope("prototype")
@Route(value = "tag", layout = MainLayout.class)
@PermitAll
public final class TagView extends VerticalLayout implements HasDynamicTitle {

    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
            new ColumnConfig("gui.header.id", List.of("id")),
            new ColumnConfig("gui.header.name", List.of("name"))
    );

    @Autowired
    private ClassAliasGenerator classAliasGenerator;
    @Autowired
    private TagViewController dataController;
    @Autowired
    private Ripper<Tag> domainRipper;
    @Autowired
    private Service<Long, Tag, Predicate, Result<List<Tag>>> tagService;
    @Autowired
    private Form<Tag> form;

    private Grid<Tag> grid;

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    @Override
    public String getPageTitle() {
        return getTranslation(classAliasGenerator.generate(getClass()));
    }

    @PostConstruct
    private void init() {
        setSizeFull();
        configureGrid();
        add(getToolBar(), getContent());
        updateList();
        removeClassName("editing");
    }

    private void configureGrid() {
        grid = new Grid<>(Tag.class);
        grid.addClassName("tag-grid");
        grid.setSizeFull();
        configureGridColumns(COLUMN_CONFIGS);
        grid.asSingleSelect().addValueChangeListener(e -> editFormValue(e.getValue()));
    }

    private void configureGridColumns(List<ColumnConfig> configList){
        grid.setColumns();
        for (ColumnConfig config : configList) {
            grid
                    .addColumn((Tag tag) -> {
                        return domainRipper.run(new DefaultRipperArg<>(tag, new ArrayDeque<>(config.getPath())));
                    })
                    .setHeader(getTranslation(config.getCode()));
        }
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
    }

    private Result<?> updateListImpl() {
        Result<List<Tag>> result = tagService.loader().all();
        if (result.isSuccess()){
            grid.setItems(result.getValue());
        }
        return result;
    }

    private Component getToolBar() {
        Button addContactButton = new Button(getTranslation("gui.button.add"));
        addContactButton.addClickListener(e -> processAddButtonClick());

        HorizontalLayout toolbar = new HorizontalLayout(addContactButton);
        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.addClassName("content");
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.setSizeFull();
        return content;
    }

    private void updateList(){
        Result<?> result = updateListImpl();
        sendNotification(result);
    }

    private void closeForm(boolean reset) {
        form.close(reset);
        removeClassName("editing");
    }

    private void addFormValue(Tag value){
        form.setValueIfItNull(value);
        addClassName("editing");
    }

    private void editFormValue(Tag value){
        if (value == null){
            closeForm(true);
        } else {
            form.setValue(value);
            addClassName("editing");
        }
    }

    public void handleSavingEvent(SaveFormEvent<TagViewController, Tag> event) {
        updateList();
        closeForm(false);
    }

    public void handleDeletingEvent(DeleteFormEvent<TagViewController, Tag> event) {
        updateList();
        closeForm(true);
    }

    public void handleCancelEvent(CloseFormEvent<TagForm, Tag> event) {
        closeForm(true);
    }

    private void sendNotification(Result<?> result) {
        if (!result.isSuccess()) {
            String text = getTranslation(result.getSeed().getCode(), result.getSeed().getArgs());
            fireEvent(new TagViewNotificationEvent(this, text, Notifications.ERROR));
        }
    }

    private void processAddButtonClick(){
        grid.asSingleSelect().clear();
        addFormValue(new Tag());
    }

    @RequiredArgsConstructor
    @Getter
    public static class ColumnConfig{
        private final String code;
        private final List<String> path;
    }
}