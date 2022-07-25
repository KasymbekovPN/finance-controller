package kpn.financecontroller.gui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import kpn.financecontroller.gui.generators.ClassAliasGenerator;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.notifications.NotificationFactory;
import kpn.financecontroller.gui.notifications.Notifications;
import kpn.financecontroller.rfunc.checker.removing.RemovingChecker;
import kpn.financecontroller.rfunc.checker.saving.SavingChecker;
import kpn.lib.domain.Domain;
import kpn.lib.result.Result;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayDeque;
import java.util.List;

abstract public class GridView<D extends Domain<Long>> extends VerticalLayout implements HasDynamicTitle {

    @Autowired
    private NotificationFactory notificationFactory;
    @Autowired
    private ClassAliasGenerator classAliasGenerator;
    @Autowired
    private SavingChecker<D> savingChecker;
    @Autowired
    private RemovingChecker<D> removingChecker;

    private Class<D> domainClass;

    protected EditForm<D> form;
    protected Grid<D> grid;

    @Override
    public String getPageTitle() {
        return getTranslation(classAliasGenerator.generate(getClass()));
    }

    @PostConstruct
    private void init() {
        setSizeFull();
        configureGrid();
        configureForm();
        add(getToolBar(), getContent());
        updateList();
        closeEditor(true);
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

    protected void updateList(){
        Result<?> result = updateListImpl();
        createNotification(result);
    }

    protected void closeEditor(boolean reset) {
        form.close(reset);
        removeClassName("editing");
    }

    protected void addValue(D value){
        form.setValueIfItNull(value);
        addClassName("editing");
    }

    protected void editValue(D value){
        if (value == null){
            closeEditor(true);
        } else {
            form.setValue(value);
            addClassName("editing");
        }
    }

    protected void handleDeletingEvent(DeleteFormEvent<EditForm<D>, D> event) {
        D domain = event.getValue();
        Result<List<D>> result = removingChecker.apply(domain);
        if (result.isSuccess()){
            result = delete(domain);
        }
        createNotification(result);
        updateList();
        closeEditor(true);
    }

    protected void handleSavingEvent(SaveFormEvent<EditForm<D>, D> event) {
        D domain = event.getValue();
        Result<List<D>> result = savingChecker.apply(domain);
        if (result.isSuccess()){
            result = save(domain);
        }
        createNotification(result);
        updateList();
        closeEditor(false);
    }

    protected void createNotification(Result<?> result) {
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

    protected void configureGridColumns(List<ColumnConfig> configList){
        grid.setColumns();
        // TODO: 25.07.2022 restore
//        for (ColumnConfig config : configList) {
//            grid
//                    .addColumn((D d) -> {return d.getInDeep(new ArrayDeque<>(config.getPath()));})
//                    .setHeader(getTranslation(config.getCode()));
//        }
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
    }

    protected void processAddButtonClick(){
        grid.asSingleSelect().clear();
        addValue(createDefaultDomain());
    }

    @SneakyThrows
    private D createDefaultDomain() {
        if (domainClass == null){
            domainClass = (Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return domainClass.getConstructor().newInstance();
    }

    protected abstract Result<?> updateListImpl();
    protected abstract void configureGrid();
    protected abstract void configureForm();
    protected abstract Result<List<D>> delete(D domain);

    protected abstract Result<List<D>> save(D domain);

    @RequiredArgsConstructor
    @Getter
    public static class ColumnConfig{
        private final String code;
        private final List<String> path;
    }
}
