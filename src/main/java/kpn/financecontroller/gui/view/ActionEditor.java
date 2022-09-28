package kpn.financecontroller.gui.view;

import com.querydsl.core.types.Predicate;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import kpn.financecontroller.data.domain.Action;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

// TODO: 28.09.2022 add handling of filter string
@Scope("prototype")
@Route(value = "editor/:UUID?")
@PermitAll
public final class ActionEditor extends VerticalLayout implements BeforeEnterObserver, BeforeLeaveObserver {
    private final Button homeButton = new Button(getTranslation("gui.button.home"));

    // TODO: 28.09.2022 to special class
    private final Dialog openDialog = new Dialog();
    private final ListBox<Action> openDialogList = new ListBox<>();

    @Autowired
    private Service<Long, Action, Predicate, Result<List<Action>>> actionService;


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // TODO: 21.09.2022 ???
        Optional<String> id = event.getRouteParameters().get("UUID");
        System.out.println("id: " + id);

        // TODO: 28.09.2022 if id is empty then navigate to Route("")
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
        // TODO: 26.09.2022 del
        System.out.println("leaving");
    }

    @PostConstruct
    private void init(){
        setSizeFull();
        add(getToolBar(), getTextArea());
        configOpenDialog();
    }

    private Component getToolBar() {
        Button openButton = new Button(getTranslation("gui.button.open"));
        openButton.addClickListener(e -> processOpenButtonClick());
        Button saveButton = new Button(getTranslation("gui.button.save"));
        openButton.addClickListener(e -> processSaveButtonClick());
        Button saveAsButton = new Button(getTranslation("gui.button.saveAs"));
        openButton.addClickListener(e -> processSaveAsButtonClick());
        Button runButton = new Button(getTranslation("gui.button.run"));
        openButton.addClickListener(e -> processRunButtonClick());
        Button disconnectButton = new Button(getTranslation("gui.button.disconnect"));
        disconnectButton.addClickListener(e -> processDisconnectButtonClick());
        homeButton.addClickListener(e -> processHomeButtonClick());

        HorizontalLayout toolbar = new HorizontalLayout(
                homeButton,
                openButton,
                saveButton,
                saveAsButton,
                runButton,
                disconnectButton
        );
        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private Component getTextArea() {
        TextArea textArea = new TextArea();
        textArea.setReadOnly(false);
        textArea.setHeight(90.0f, Unit.PERCENTAGE);
        textArea.setWidthFull();
        return textArea;
    }

    private void configOpenDialog() {
        openDialog.setCloseOnOutsideClick(true);
        openDialog.setCloseOnEsc(true);

        openDialog.setHeaderTitle(getTranslation("gui.title.choose-action"));

        TextField filter = new TextField(getTranslation("gui.label.filter"), getTranslation("gui.placeHolder.type-filter"));
        openDialogList.setItemLabelGenerator(new ItemLabelGenerator<Action>() {
            @Override
            public String apply(Action item) {
                return item.getDescription();
            }
        });

        openDialog.add(filter, openDialogList);

        Button cancelButton = new Button(getTranslation("gui.button.cancel"), e -> openDialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

        // TODO: 28.09.2022 add handler
        Button openButton = new Button(getTranslation("gui.button.open"), e -> processOpenDialogOpenButton());

        openDialog.getFooter().add(cancelButton, openButton);
    }

    private void processOpenButtonClick() {
        // TODO: 19.09.2022 del
        System.out.println("click open");

        Result<List<Action>> result = actionService.loader().all();
        if (result.isSuccess()){
            openDialogList.setItems(result.getValue());
            openDialog.open();
        } else {
            // TODO: 28.09.2022 notice about error
            System.out.println(result.getSeed());
        }
    }

    private void processSaveButtonClick() {
        // TODO: 19.09.2022 impl
    }

    private void processSaveAsButtonClick() {
        // TODO: 19.09.2022 impl
    }

    private void processRunButtonClick() {
        // TODO: 19.09.2022 impl
    }

    private void processDisconnectButtonClick() {
        // TODO: 26.09.2022 impl
    }

    private void processHomeButtonClick() {
        homeButton.getUI().ifPresent(ui -> {
            ui.navigate(PaymentView.class);
        });
    }

    private void processOpenDialogOpenButton() {
        // TODO: 28.09.2022 del
        System.out.println("processOpenDialogOpenButton");

        // TODO: 28.09.2022 impl
    }
}
