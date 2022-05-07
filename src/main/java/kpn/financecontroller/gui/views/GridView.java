package kpn.financecontroller.gui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import kpn.financecontroller.rfunc.checker.removing.RemovingChecker;
import kpn.financecontroller.rfunc.checker.saving.SavingChecker;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.generators.ClassAliasGenerator;
import kpn.financecontroller.gui.notifications.NotificationFactory;
import kpn.financecontroller.gui.notifications.Notifications;
import kpn.lib.result.Result;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

abstract public class GridView<D> extends VerticalLayout implements HasDynamicTitle {

    @Autowired
    private NotificationFactory notificationFactory;
    @Autowired
    private ClassAliasGenerator classAliasGenerator;
    @Autowired
    private SavingChecker<D> savingChecker;
    @Autowired
    private RemovingChecker<D> removingChecker;

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
        closeEditor();
    }

    private Component getToolBar() {
        Button addContactButton = new Button(getTranslation("gui.button.add"));
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

    protected void handleDeletingEvent(DeleteFormEvent<EditForm<D>, D> event) {
        D domain = event.getValue();
        Result<Void> result = removingChecker.apply(domain);
        if (result.isSuccess()){
            result = delete(domain);
        }
        createNotification(result);
        updateList();
        closeEditor();
    }

    protected void handleSavingEvent(SaveFormEvent<EditForm<D>, D> event) {
        D domain = event.getValue();
        Result<D> result = savingChecker.apply(domain);
        if (result.isSuccess()){
            result = save(domain);
        }
        createNotification(result);
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
    protected abstract Result<Void> delete(D domain);
    protected abstract Result<D> save(D domain);
}
