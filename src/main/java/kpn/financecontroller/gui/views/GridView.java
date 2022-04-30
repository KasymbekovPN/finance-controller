package kpn.financecontroller.gui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.notifications.NotificationFactory;
import kpn.financecontroller.gui.notifications.Notifications;
import kpn.lib.result.Result;

import javax.annotation.PostConstruct;

abstract public class GridView<D> extends VerticalLayout implements HasDynamicTitle {

    protected final Grid<D> grid;

    private final NotificationFactory notificationFactory;
    private final String titleCode;

    protected EditForm<D> form;

    public GridView(Grid<D> grid, NotificationFactory notificationFactory, String titleCode) {
        this.grid = grid;
        this.notificationFactory = notificationFactory;
        this.titleCode = titleCode;
    }

    @Override
    public String getPageTitle() {
        return getTranslation(titleCode);
    }

    @PostConstruct
    private void init() {
        setSizeFull();
        configureGrid();
        configureForm();
        add(getToolBar(), getContent());
        updateList();
        closeEditor();
    }

    private Component getToolBar() {
        Button addContactButton = new Button(getTranslation("gui.add"));
        addContactButton.addClickListener(e -> add());

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

    protected void closeEditor() {
        form.setValue(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    protected void editValue(D value){
        if (value == null){
            closeEditor();
        } else {
            form.setValue(value);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    protected void deleteEvent(DeleteFormEvent<EditForm<D>, D> event) {
        Result<Void> deletingResult = handleDeleteEvent(event);
        createNotification(deletingResult);
        updateList();
        closeEditor();
    }

    protected void saveContact(SaveFormEvent<EditForm<D>, D> event) {
        Result<D> savingResult = handleSaveEvent(event);
        createNotification(savingResult);
        updateList();
        closeEditor();
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

    protected abstract Result<?> updateListImpl();
    protected abstract void configureGrid();
    protected abstract void configureForm();
    protected abstract void add();
    protected abstract Result<Void> handleDeleteEvent(DeleteFormEvent<EditForm<D>, D> event);
    protected abstract Result<D> handleSaveEvent(SaveFormEvent<EditForm<D>, D> event);
}
