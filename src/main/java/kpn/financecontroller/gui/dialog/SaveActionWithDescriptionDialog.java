package kpn.financecontroller.gui.dialog;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;

public final class SaveActionWithDescriptionDialog extends Dialog {
    private final Button cancelButton = new Button(getTranslation("gui.button.cancel"));
    private final Button saveButton = new Button(getTranslation("gui.button.save"));
    private final TextField description = new TextField(getTranslation("gui.label.description"));

    public SaveActionWithDescriptionDialog(String titleCode) {
        setCloseOnOutsideClick(true);
        setCloseOnEsc(true);
        setHeaderTitle(getTranslation(titleCode));
        add(description);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        getFooter().add(cancelButton, saveButton);
    }

    @Override
    public void close() {
        description.clear();
        super.close();
    }

    public void addCancelButtonClickListener(ComponentEventListener<ClickEvent<Button>> listener){
        cancelButton.addClickListener(listener);
    }

    public void addSaveButtonClickListener(ComponentEventListener<ClickEvent<Button>> listener){
        saveButton.addClickListener(listener);
    }

    public void addDescriptionValueChangeListener(HasValue.ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<TextField, String>> listener){
        description.addValueChangeListener(listener);
    }
}
