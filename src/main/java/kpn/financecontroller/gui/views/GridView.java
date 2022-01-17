package kpn.financecontroller.gui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.notifications.NotificationFactory;
import kpn.financecontroller.gui.notifications.Notifications;
import kpn.financecontroller.i18n.I18nService;
import kpn.financecontroller.message.LocaledMessageSeed;
import kpn.financecontroller.message.LocaledMessageSeedFactory;
import kpn.financecontroller.result.Result;

import javax.annotation.PostConstruct;

abstract public class GridView<D> extends VerticalLayout {

    protected final Grid<D> grid;

    private final LocaledMessageSeedFactory seedFactory;
    private final I18nService i18nService;
    private final NotificationFactory notificationFactory;

    protected EditForm<D> form;

    public GridView(Grid<D> grid,
                    LocaledMessageSeedFactory seedFactory,
                    I18nService i18nService,
                    NotificationFactory notificationFactory) {
        this.grid = grid;
        this.seedFactory = seedFactory;
        this.i18nService = i18nService;
        this.notificationFactory = notificationFactory;
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
        Button addContactButton = new Button("Add");
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

    private void createNotification(Result<?> result) {
        if (!result.getSuccess()){
            LocaledMessageSeed seed = seedFactory.create(result);
            String text = i18nService.getTranslation(seed);
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
