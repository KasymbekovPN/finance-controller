package kpn.financecontroller.gui.views.tag;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.gui.views.tag.event.TagCancelButtonClickEvent;
import kpn.financecontroller.gui.views.tag.event.TagDeleteButtonOnClickEvent;
import kpn.financecontroller.gui.views.tag.event.TagSaveButtonOnClickEvent;
import org.springframework.context.annotation.Scope;

@org.springframework.stereotype.Component
@Scope("prototype")
public final class TagForm extends FormLayout {

    private final Button save = new Button();
    private final Button delete = new Button();
    private final Button cancel = new Button();
    private final Binder<Tag> binder;
    private final TextField name = new TextField();

    private Tag value;

    public TagForm() {
        binder = new Binder<>(Tag.class);
        addClassName("tag-form");
        binder.bindInstanceFields(this);

        name.setLabel(getTranslation("gui.label.name"));
        name.setPlaceholder(getTranslation("gui.placeholder.type-name"));

        add(
                name,
                createButtonsLayout()
        );

        setWidth("25em");
        close(true);
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    public void setValueIfItNull(Tag value) {
        setValue(this.value == null ? value : this.value);
    }

    public void setValue(Tag value) {
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

    private Component createButtonsLayout() {
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
        delete.addClickListener(event -> fireEvent(new TagDeleteButtonOnClickEvent(this, value)));
    }

    private void customizeCancelButton() {
        cancel.setText(getTranslation("gui.button.cancel"));
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancel.addClickShortcut(Key.ESCAPE);
        cancel.addClickListener(event -> fireEvent(new TagCancelButtonClickEvent(this)));
    }

    private void processSaveButtonClick() {
        try{
           binder.writeBean(value);
           fireEvent(new TagSaveButtonOnClickEvent(this, value));
        } catch (ValidationException ex){
           ex.printStackTrace();
        }
    }
}
