package kpn.financecontroller.gui.dialog;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;

public final class SaveActionDialog extends Dialog {
    private final Button cancelButton = new Button(getTranslation("gui.button.cancel"));
    private final Button saveButton = new Button(getTranslation("gui.button.save"));

    public SaveActionDialog() {
        setCloseOnOutsideClick(true);
        setCloseOnEsc(true);
        setHeaderTitle(getTranslation("gui.title.save?"));
        cancelButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        getFooter().add(cancelButton, saveButton);
    }

    public void addCancelButtonClickListener(ComponentEventListener<ClickEvent<Button>> listener){
        cancelButton.addClickListener(listener);
    }

    public void addSaveButtonClickListener(ComponentEventListener<ClickEvent<Button>> listener){
        saveButton.addClickListener(listener);
    }
}
