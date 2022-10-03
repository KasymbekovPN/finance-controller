package kpn.financecontroller.gui.view;

import com.querydsl.core.types.Predicate;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import kpn.financecontroller.data.domain.Action;
import kpn.financecontroller.gui.binding.editor.EditorToActionBinder;
import kpn.financecontroller.gui.dialog.OpenActionDialog;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import java.util.List;
import java.util.Optional;

@Slf4j
@Scope("prototype")
@Route(value = "editor/:UUID?")
@PermitAll
public final class ActionEditor extends VerticalLayout implements BeforeEnterObserver, BeforeLeaveObserver, AfterNavigationObserver {
    private final OpenDialogHandler openDialogHandler = new OpenDialogHandler();

    private final Button homeButton = new Button(getTranslation("gui.button.home"));

    private final OpenActionDialog openDialog = new OpenActionDialog();

    @Autowired
    private Service<Long, Action, Predicate, Result<List<Action>>> actionService;
    @Autowired
    private EditorToActionBinder<String, Integer> binder;

    private String id;
    private boolean toHome;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> maybeId = event.getRouteParameters().get("UUID");
        log.info("UUID: {}", maybeId);

        if (maybeId.isEmpty() || binder.checkKey(maybeId.get())){
            log.warn("Key {} is registered or empty", maybeId); // TODO: 01.10.2022 ???
            toHome = true;
        } else {
            id = maybeId.get();
            binder.registerKey(id);

            log.info("Key {} is set", id); // TODO: 01.10.2022 ???
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        if (toHome){
            log.info("Navigate to home...");
            getUI().ifPresent(ui -> {ui.navigate(PaymentView.class);});

            // TODO: 03.10.2022 notification
        }
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
        log.info("Leaving..."); // TODO: 03.10.2022 ???
        binder.unregister(id);
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
        openDialog.addCancelButtonClickListener(openDialogHandler::handleClosing);
        openDialog.addOpenButtonClickListener(openDialogHandler::handleOpening);
        openDialog.addFilterValueChangeListener(openDialogHandler::handleFilterChanging);
        openDialog.addListValueChangeListener(openDialogHandler::handleListChanging);
    }

    private void processOpenButtonClick() {
        log.info("Open button is pressed"); // TODO: 03.10.2022 ???

        Result<List<Action>> result = actionService.loader().all();
        if (result.isSuccess()){
            openDialog.setItems(result.getValue());
            openDialog.open();
        } else {
            // TODO: 28.09.2022 notification
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
        log.info("Home button is pressed"); // TODO: 03.10.2022 ???
        homeButton.getUI().ifPresent(ui -> {
            ui.navigate(PaymentView.class);
        });
    }

    private class OpenDialogHandler {
        public void handleClosing(ClickEvent<Button> buttonClickEvent) {
            log.info("closing"); // TODO: 01.10.2022 ???
            openDialog.close();
        }

        public void handleOpening(ClickEvent<Button> buttonClickEvent) {
            log.info("opening"); // TODO: 01.10.2022  ???

            // TODO: 01.10.2022 impl
        }

        public void handleFilterChanging(AbstractField.ComponentValueChangeEvent<TextField, String> event) {
            log.info("filter changing {} -> {}", event.getOldValue(), event.getValue()); // TODO: 01.10.2022 ???

            // TODO: 01.10.2022 impl
        }

        public void handleListChanging(AbstractField.ComponentValueChangeEvent<ListBox<Action>, Action> event) {
            log.info("list value changing {} -> {}", event.getOldValue(), event.getValue());

            // TODO: 01.10.2022 impl
        }
    }
}
