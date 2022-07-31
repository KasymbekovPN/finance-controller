package kpn.financecontroller.gui.views.tag;

import com.querydsl.core.types.Predicate;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.gui.events.CloseFormEvent;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.generators.ClassAliasGenerator;
import kpn.financecontroller.gui.notifications.NotificationFactory;
import kpn.financecontroller.gui.notifications.Notifications;
import kpn.financecontroller.gui.views.MainLayout;
import kpn.financecontroller.gui.views.tag.controller.TagDataController;
import kpn.financecontroller.gui.views.tag.event.*;
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
    private NotificationFactory notificationFactory;
    @Autowired
    private ClassAliasGenerator classAliasGenerator;
    @Autowired
    private TagDataController dataController;

    // TODO: 30.07.2022 del
//    @Autowired
//    private SavingChecker<D> savingChecker;
//    @Autowired
//    private RemovingChecker<D> removingChecker;
    @Autowired
    private Ripper<Tag> domainRipper;
//
//    private Class<D> domainClass;
    @Autowired
    private Service<Long, Tag, Predicate, Result<List<Tag>>> tagService;

    @Autowired
    private TagForm form;
    private Grid<Tag> grid;

    @Override
    public String getPageTitle() {
        return getTranslation(classAliasGenerator.generate(getClass()));
    }

    @PostConstruct
    private void init() {
        // TODO: 31.07.2022 del log
        System.out.println("----------------------------------------" + toString());

        setSizeFull();
        configureGrid();
//        configureForm(); // TODO: 31.07.2022 del
        bindEvent();
        add(getToolBar(), getContent());
        updateList();
//        closeForm(true); // TODO: 31.07.2022 del
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

    // TODO: 31.07.2022 del
//    private void configureForm() {
//        // TODO: 31.07.2022 ??
////        form = new TagForm();
//        form.setWidth("25em");
//    }

    // TODO: 30.07.2022 move ???
    private void bindEvent() {
        form.addListener(TagSaveButtonOnClickEvent.class, dataController::handleSavingEvent);
        dataController.addListener(TagSaveReactionEvent.class, this::handleSavingEvent);
        form.addListener(TagDeleteButtonOnClickEvent.class, dataController::handleDeletingEvent);
        dataController.addListener(TagDeleteReactionEvent.class, this::handleDeletingEvent);
        form.addListener(TagCancelButtonClickEvent.class, this::handleCancelEvent);
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
        createNotification(result);
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

    private void handleSavingEvent(SaveFormEvent<TagDataController, Tag> event) {
        updateList();
        closeForm(false);
    }

    private void handleDeletingEvent(DeleteFormEvent<TagDataController, Tag> event) {
        updateList();
        closeForm(true);
    }

    private void handleCancelEvent(CloseFormEvent<TagForm, Tag> event) {
        closeForm(true);
    }

    // TODO: 30.07.2022 bean
    private void createNotification(Result<?> result) {
        if (!result.isSuccess()){
            String text = getTranslation(result.getSeed().getCode(), result.getSeed().getArgs());
            notificationFactory.getBuilder(Notifications.ERROR)
                    .duration(60_000)
                    .text(text)
                    .buttonIcon(new Icon("lumo", "cross"))
                    .buttonThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE)
                    .buttonAttribute("aria-label", "Close")
                    .build()
                    .open();
        }
    }

    private void processAddButtonClick(){
        grid.asSingleSelect().clear();
        addFormValue(new Tag());
    }

    // TODO: 30.07.2022 ???
//    protected abstract Result<?> updateListImpl();
//    protected abstract void configureGrid();
//    protected abstract void configureForm();
//    protected abstract Result<List<D>> delete(D domain);
//
//    protected abstract Result<List<D>> save(D domain);
//
    @RequiredArgsConstructor
    @Getter
    public static class ColumnConfig{
        private final String code;
        private final List<String> path;
    }

    // TODO: 30.07.2022 del
//    public class Xirita {
//
//        public void hadleSavingEvent(SaveFormEvent<TagView, Tag> ev) {
//            ev.getValue();
//        }
//    }
}
