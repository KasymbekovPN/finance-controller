package kpn.financecontroller.gui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import kpn.financecontroller.gui.events.CloseFormEvent;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;

abstract public class EditForm<DOMAIN> extends FormLayout {

    protected final Button save = new Button();
    protected final Button delete = new Button();
    protected final Button close = new Button();
    protected final Binder<DOMAIN> binder;

    protected DOMAIN value;

    protected EditForm(Binder<DOMAIN> binder) {
        this.binder = binder;
    }

    public void setValueIfItNull(DOMAIN value) {
        setValue(this.value == null ? value : this.value);
    }

    public void setValue(DOMAIN value) {
        this.value = value;
        binder.readBean(value);
        setVisible(true);
    }

    public void close(boolean reset){
        if (reset){
            setValue(null);
        }
        setVisible(false);
    }

    protected Component createButtonsLayout() {
        save.setText(getTranslation("gui.button.save"));
        delete.setText(getTranslation("gui.button.delete"));
        close.setText(getTranslation("gui.button.cancel"));

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> processSaveButtonClick());
        delete.addClickListener(event ->fireEvent(createDeleteEvent()));
        close.addClickListener(event -> fireEvent(createCloseEvent()));

        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
     }

    protected void processSaveButtonClick() {
        try{
           binder.writeBean(value);
           fireEvent(createSaveEvent());
        } catch (ValidationException ex){
           ex.printStackTrace();
        }
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    protected abstract SaveFormEvent<EditForm<DOMAIN>, DOMAIN> createSaveEvent();
    protected abstract DeleteFormEvent<EditForm<DOMAIN>, DOMAIN> createDeleteEvent();
    protected abstract CloseFormEvent<EditForm<DOMAIN>, DOMAIN> createCloseEvent();
}
