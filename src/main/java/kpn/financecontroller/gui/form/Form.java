package kpn.financecontroller.gui.form;

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
import kpn.lib.domain.Domain;

public abstract class Form<D extends Domain<Long>> extends FormLayout {

    private final Button save = new Button();
    private final Button delete = new Button();
    private final Button cancel = new Button();
    protected final Binder<D> binder;

    protected D value;

    public Form(Binder<D> binder) {
        this.binder = binder;
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    public void setValueIfItNull(D value) {
        setValue(this.value == null ? value : this.value);
    }

    public void setValue(D value) {
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

    // TODO: 15.08.2022 ???
    public void resize(){}

    protected Component createButtonsLayout() {
        customizeSaveButton();
        customizeDeleteButton();
        customizeCancelButton();
        return new HorizontalLayout(save, delete, cancel);
    }

    private void customizeSaveButton() {
        save.setText(getTranslation("gui.button.save"));
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);
        save.addClickListener(event -> processSaveButtonClick());
        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));
    }

    private void customizeDeleteButton() {
        delete.setText(getTranslation("gui.button.delete"));
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.addClickListener(event -> fireEvent(createDeleteButtonOnClickEvent()));
    }

    private void customizeCancelButton() {
        cancel.setText(getTranslation("gui.button.cancel"));
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancel.addClickShortcut(Key.ESCAPE);
        cancel.addClickListener(event -> fireEvent(createCancelButtonOnClickEvent()));
    }

    private void processSaveButtonClick() {
        try{
           binder.writeBean(value);
           fireEvent(createSaveButtonOnClickEvent());
        } catch (ValidationException ex){
           ex.printStackTrace();
        }
    }

    protected abstract ComponentEvent<?> createSaveButtonOnClickEvent();
    protected abstract ComponentEvent<?> createCancelButtonOnClickEvent();
    protected abstract ComponentEvent<?> createDeleteButtonOnClickEvent();
}
