package kpn.financecontroller.gui.dialog;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.textfield.TextField;
import kpn.financecontroller.data.domain.Action;

import java.util.Collection;

public class OpenActionDialog extends Dialog {
    private final ListBox<Action> list = new ListBox<>();
    private final Button cancelButton = new Button(getTranslation("gui.button.cancel"));
    private final Button openButton = new Button(getTranslation("gui.button.open"));
    private final TextField filter = new TextField(getTranslation("gui.label.filter"), getTranslation("gui.placeHolder.type-filter"));


    public OpenActionDialog() {
        setCloseOnOutsideClick(true);
        setCloseOnEsc(true);
        setHeaderTitle(getTranslation("gui.title.choose-action"));

        list.setItemLabelGenerator(new ItemLabelGenerator<Action>() {
            @Override
            public String apply(Action item) {
                return item.getDescription();
            }
        });

        add(filter, list);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

        getFooter().add(cancelButton, openButton);
    }

    public void setItems(Collection<Action> value) {
        list.setItems(value);
    }

    public void addCancelButtonClickListener(ComponentEventListener<ClickEvent<Button>> listener){
        cancelButton.addClickListener(listener);
    }

    public void addOpenButtonClickListener(ComponentEventListener<ClickEvent<Button>> listener){
        openButton.addClickListener(listener);
    }

    public void addFilterValueChangeListener(HasValue.ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<TextField, String>> listener){
        filter.addValueChangeListener(listener);
    }

    public void addListValueChangeListener(HasValue.ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<ListBox<Action>, Action>> listener){
        list.addValueChangeListener(listener);
    }


    // TODO: 28.09.2022 del
//        openDialog.setCloseOnOutsideClick(true);
//        openDialog.setCloseOnEsc(true);
//
//        openDialog.setHeaderTitle(getTranslation("gui.title.choose-action"));
//
//        TextField filter = new TextField(getTranslation("gui.label.filter"), getTranslation("gui.placeHolder.type-filter"));
//        openDialogList.setItemLabelGenerator(new ItemLabelGenerator<Action>() {
//            @Override
//            public String apply(Action item) {
//                return item.getDescription();
//            }
//        });
//
//        openDialog.add(filter, openDialogList);
//
//        Button cancelButton = new Button(getTranslation("gui.button.cancel"), e -> openDialog.close());
//        cancelButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
//
//        // TODO: 28.09.2022 add handler
//        Button openButton = new Button(getTranslation("gui.button.open"), e -> processOpenDialogOpenButton());
//
//        openDialog.getFooter().add(cancelButton, openButton);
}
