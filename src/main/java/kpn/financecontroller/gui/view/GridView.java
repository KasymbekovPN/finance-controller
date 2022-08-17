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
import com.vaadin.flow.shared.Registration;
import kpn.financecontroller.gui.controller.ViewController;
import kpn.financecontroller.gui.event.CancelEvent;
import kpn.financecontroller.gui.event.DeleteEvent;
import kpn.financecontroller.gui.event.SaveEvent;
import kpn.financecontroller.gui.form.Form;
import kpn.financecontroller.gui.generators.ClassAliasGenerator;
import kpn.lib.domain.Domain;
import kpn.lib.result.Result;
import kpn.lib.ripper.DefaultRipperArg;
import kpn.lib.ripper.Ripper;
import kpn.lib.service.Service;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayDeque;
import java.util.List;

public abstract class GridView<D extends Domain<Long>> extends VerticalLayout implements HasDynamicTitle {
    @Autowired
    private ClassAliasGenerator classAliasGenerator;
    @Autowired
    private Ripper<D> domainRipper;
    @Autowired
    private Service<Long, D, Predicate, Result<List<D>>> service;
    @Autowired
    protected Form<D> form;

    protected Grid<D> grid;

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

    protected void configureGrid() {
        grid = createGrid();
        grid.setSizeFull();
        configureGridColumns(getConfigList());
        grid.asSingleSelect().addValueChangeListener(e -> editFormValue(e.getValue()));
    }

    protected void configureGridColumns(List<ColumnConfig> configList){
        grid.setColumns();
        for (ColumnConfig config : configList) {
            grid
                    .addColumn((D tag) -> {
                        return domainRipper.run(new DefaultRipperArg<>(tag, new ArrayDeque<>(config.getPath())));
                    })
                    .setHeader(getTranslation(config.getCode()));
        }
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
    }

    private Component getToolBar() {
        Button addContactButton = new Button(getTranslation("gui.button.add"));
        addContactButton.addClickListener(e -> processAddButtonClick());

        HorizontalLayout toolbar = new HorizontalLayout(addContactButton);
        toolbar.addClassName("toolbar");

        return toolbar;
    }

    // TODO: 15.08.2022 ???
    protected Component getContent() {
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

    private Result<?> updateListImpl() {
        Result<List<D>> result = service.loader().all();
        if (result.isSuccess()){
            grid.setItems(result.getValue());
        }
        return result;
    }

    private void closeForm(boolean reset) {
        form.close(reset);
        removeClassName("editing");
    }

    private void addFormValue(D value){
        form.setValueIfItNull(value);
        addClassName("editing");
    }

    protected void editFormValue(D value){
        if (value == null){
            closeForm(true);
        } else {
            form.setValue(value);
            addClassName("editing");
        }
    }

    public <F extends ViewController<D>> void handleSavingEvent(SaveEvent<F, D> event) {
        updateList();
        closeForm(false);
    }

    public <F extends ViewController<D>> void handleDeletingEvent(DeleteEvent<F, D> event) {
        updateList();
        closeForm(true);
    }

    public <F extends Form<D>> void handleCancelEvent(CancelEvent<F, D> event) {
        closeForm(true);
    }

    private void sendNotification(Result<?> result) {
        if (!result.isSuccess()) {
            String text = getTranslation(result.getSeed().getCode(), result.getSeed().getArgs());
            fireEvent(createNotificationEvent(text));
        }
    }

    // TODO: 15.08.2022 ???
    protected void processAddButtonClick(){
        grid.asSingleSelect().clear();
        addFormValue(createDomain());
    }


    protected abstract Grid<D> createGrid();
    protected abstract ComponentEvent<?> createNotificationEvent(String text);
    protected abstract D createDomain();
    protected abstract List<ColumnConfig> getConfigList();

    @RequiredArgsConstructor
    @Getter
    public static class ColumnConfig{
        private final String code;
        private final List<String> path;
    }
}
