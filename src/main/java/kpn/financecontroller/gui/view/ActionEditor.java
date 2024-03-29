package kpn.financecontroller.gui.view;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.shared.Registration;
import kpn.financecontroller.data.domain.Action;
import kpn.financecontroller.data.services.dto.service.ActionDtoDecorator;
import kpn.financecontroller.gui.binding.util.Binder;
import kpn.financecontroller.gui.dialog.OpenActionDialog;
import kpn.financecontroller.gui.dialog.SaveActionDialog;
import kpn.financecontroller.gui.dialog.SaveActionWithDescriptionDialog;
import kpn.financecontroller.gui.event.action.editor.ActionEditorNotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.lib.result.Result;
import kpn.lib.seed.ImmutableSeed;
import kpn.lib.seed.Seed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Scope("prototype")
@Route(value = "editor/:UUID?")
@PermitAll
public final class ActionEditor extends VerticalLayout implements BeforeEnterObserver, BeforeLeaveObserver, AfterNavigationObserver {
    private final OpenDialogHandler openDialogHandler = new OpenDialogHandler();
    private final SaveDialogHandler saveDialogHandler = new SaveDialogHandler();
    private final SaveAsDialogHandler saveAsDialogHandler = new SaveAsDialogHandler();
    private final NewDialogHandler newDialogHandler = new NewDialogHandler();

    private final Button homeButton = new Button(getTranslation("gui.button.home"));

    private final OpenActionDialog openDialog = new OpenActionDialog();
    private final SaveActionDialog saveDialog = new SaveActionDialog();
    private final SaveActionWithDescriptionDialog saveAsDialog = new SaveActionWithDescriptionDialog("gui.title.saveAs?");
    private final SaveActionWithDescriptionDialog newDialog = new SaveActionWithDescriptionDialog("gui.title.create-new?");
    private final TextArea editor = new TextArea();

    @Autowired
    private ActionDtoDecorator actionService;
    @Autowired
    @Qualifier("uuidToEditorBinder")
    private Binder<String, Integer> uuidToEditorBinder;
    @Autowired
    @Qualifier("actionIdToUuidBinder")
    private Binder<Long, String> actionIdToUuidBinder;

    @Autowired
    @Qualifier("displayIdToActionIdBinder")
    private Binder<String, Long> displayIdToActionIdBinder;

    private TextArea descriptionArea;
    private String id;
    private boolean toHome;
    private Action selectedAction;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> maybeId = event.getRouteParameters().get("UUID");
        log.info("UUID: {}", maybeId);

        if (maybeId.isEmpty() || !uuidToEditorBinder.bind(maybeId.get(), this.hashCode())){
            log.warn("Key {} is registered or empty", maybeId);
            toHome = true;
        } else {
            id = maybeId.get();
            log.info("Key {} is set", id);
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        if (toHome){
            log.info("Navigate to home...");
            getUI().ifPresent(ui -> {ui.navigate(PaymentView.class);});
            fireEvent(createNotificationEvent("action-editor.uuid.not-unique"));
        }
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
        log.info("Leaving...");
        uuidToEditorBinder.unbind(id);
        if (selectedAction != null){
            actionIdToUuidBinder.unbind(selectedAction.getId());
        }
    }

    @PostConstruct
    private void init(){
        setSizeFull();
        add(getToolBar(), getDescriptionArea(), getTextArea());
        configOpenDialog();
        configSaveDialog();
        configSaveAsDialog();
        configNewDialog();
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    private Component getToolBar() {
        Button newButton = new Button(getTranslation("gui.button.new"));
        newButton.addClickListener(e -> processNewButtonClick());
        Button openButton = new Button(getTranslation("gui.button.open"));
        openButton.addClickListener(e -> processOpenButtonClick());
        Button saveButton = new Button(getTranslation("gui.button.save"));
        saveButton.addClickListener(e -> processSaveButtonClick());
        Button saveAsButton = new Button(getTranslation("gui.button.saveAs"));
        saveAsButton.addClickListener(e -> processSaveAsButtonClick());
        Button displayButton = new Button(getTranslation("gui.button.display"));
        displayButton.addClickListener(e -> processDisplayButtonClick());
        Button closeButton = new Button(getTranslation("gui.button.close"));
        closeButton.addClickListener(e -> processCloseButtonClick());
        homeButton.addClickListener(e -> processHomeButtonClick());

        HorizontalLayout toolbar = new HorizontalLayout(
                homeButton,
                newButton,
                openButton,
                closeButton,
                saveButton,
                saveAsButton,
                displayButton
        );
        toolbar.addClassName("toolbar");
        toolbar.setWidthFull();

        return toolbar;
    }

    private Component getDescriptionArea() {
        descriptionArea = new TextArea();
        descriptionArea.setReadOnly(true);
        descriptionArea.setWidthFull();

        return descriptionArea;
    }

    private Component getTextArea() {
        editor.setReadOnly(false);
        editor.setHeight(90.0f, Unit.PERCENTAGE);
        editor.setWidthFull();
        return editor;
    }

    private void configOpenDialog() {
        openDialog.addCancelButtonClickListener(openDialogHandler::handleCloseButtonClick);
        openDialog.addOpenButtonClickListener(openDialogHandler::handleOpenButtonClick);
        openDialog.addFilterValueChangeListener(openDialogHandler::handleFilterChanging);
        openDialog.addListValueChangeListener(openDialogHandler::handleListChanging);
    }

    private void configSaveDialog() {
        saveDialog.addCancelButtonClickListener(saveDialogHandler::handleCloseButtonClick);
        saveDialog.addSaveButtonClickListener(saveDialogHandler::handleSaveButtonClick);
    }

    private void configSaveAsDialog() {
        saveAsDialog.addCancelButtonClickListener(saveAsDialogHandler::handleCloseButtonClick);
        saveAsDialog.addSaveButtonClickListener(saveAsDialogHandler::handleSaveButtonClick);
        saveAsDialog.addDescriptionValueChangeListener(saveAsDialogHandler::handleDescriptionChanging);
        saveAsDialog.addDialogCloseActionListener(saveAsDialogHandler::handleClosing);
    }

    private void configNewDialog() {
        newDialog.addCancelButtonClickListener(newDialogHandler::handleCloseButtonClick);
        newDialog.addSaveButtonClickListener(newDialogHandler::handleSaveButtonClick);
        newDialog.addDescriptionValueChangeListener(newDialogHandler::handleDescriptionChanging);
        newDialog.addDialogCloseActionListener(newDialogHandler::handleClosing);
    }

    private void setSelectedAction(Action action) {
        selectedAction = action;
        editor.setValue(action.getAlgorithm());
        descriptionArea.setValue(action.getDescription());
    }

    private void processNewButtonClick() {
        log.info("New button is pressed");
        if (selectedAction == null){
            newDialog.open();
        } else {
            fireEvent(createNotificationEvent("action-editor.click-new-button.action-selected"));
        }
    }

    private void processOpenButtonClick() {
        log.info("Open button is pressed");

        Result<List<Action>> result = actionService.loader().all();
        if (result.isSuccess()){
            openDialog.setItems(result.getValue());
            openDialog.open();
        } else {
            fireEvent(createNotificationEvent("action-editor.click-open-button.fail"));
            fireEvent(createNotificationEvent(result.getSeed()));
        }
    }

    private void processSaveButtonClick() {
        log.info("Save button is pressed");
        if (selectedAction != null){
            saveDialog.open();
        } else {
            fireEvent(createNotificationEvent("action-editor.click-save-button.selected-null"));
        }
    }

    private void processSaveAsButtonClick() {
        log.info("Save as button is pressed");
        if (selectedAction != null){
            saveAsDialog.open();
        } else {
            fireEvent(createNotificationEvent("action-editor.click-save-as-button.selected-null"));
        }
    }

    private void processDisplayButtonClick() {
        log.info("Display button is clicked");
        if (selectedAction != null){
            getUI().ifPresentOrElse(
                    ui -> {
                        String routeValue = ActionDisplay.class.getAnnotation(Route.class).value();
                        String displayId = String.valueOf(UUID.randomUUID());
                        displayIdToActionIdBinder.bind(displayId, selectedAction.getId());
                        String url = routeValue.replace(":UUID?", displayId);
                        ui.getPage().open(url, "_blank");
                    },
                    () -> {
                        log.warn("UI is not present");
                    }
            );
        } else {
            fireEvent(createNotificationEvent("action-editor.display-button.selected-null"));
        }
    }

    private void processCloseButtonClick() {
        log.info("Close");
        editor.clear();
        descriptionArea.clear();
        if (selectedAction != null){
            actionIdToUuidBinder.unbind(selectedAction.getId());
            selectedAction = null;
        }
    }

    private void processHomeButtonClick() {
        log.info("Home button is pressed");
        homeButton.getUI().ifPresent(ui -> {
            ui.navigate(PaymentView.class);
        });
    }

    private ActionEditorNotificationEvent createNotificationEvent(String code, NotificationType type){
        return new ActionEditorNotificationEvent(
                this,
                ImmutableSeed.builder().code(code).build(),
                type
        );
    }

    private ActionEditorNotificationEvent createNotificationEvent(String code){
        return new ActionEditorNotificationEvent(
                this,
                ImmutableSeed.builder().code(code).build(),
                NotificationType.ERROR
        );
    }

    private ActionEditorNotificationEvent createNotificationEvent(Seed seed){
        return new ActionEditorNotificationEvent(this, seed, NotificationType.ERROR);
    }

    private class OpenDialogHandler {
        private Action action;

        public void handleCloseButtonClick(ClickEvent<Button> buttonClickEvent) {
            log.info("closing");
            handleClosing();
        }

        public void handleOpenButtonClick(ClickEvent<Button> buttonClickEvent) {
            log.info("opening");
            if (action != null){
                if (actionIdToUuidBinder.bind(action.getId(), id)){
                    setSelectedAction(action);
                } else {
                    fireEvent(createNotificationEvent("action-editor.open-dialog.action-busy"));
                }
                handleClosing();
            }
        }

        public void handleFilterChanging(AbstractField.ComponentValueChangeEvent<TextField, String> event) {
            log.info("filter changing {} -> {}", event.getOldValue(), event.getValue());
            Result<List<Action>> result = actionService.findByDescriptionSubstring(event.getValue());
            openDialog.setItems(result.isSuccess() ? result.getValue() : List.of());
        }

        public void handleListChanging(AbstractField.ComponentValueChangeEvent<ListBox<Action>, Action> event) {
            log.info("list value changing {} -> {}", event.getOldValue(), event.getValue());
            action = event.getValue();
        }

        private void handleClosing() {
            action = null;
            openDialog.close();
        }
    }

    private class SaveDialogHandler {

        public void handleCloseButtonClick(ClickEvent<Button> buttonClickEvent) {
            log.info("Closing");
            closeDialog();
        }

        public void handleSaveButtonClick(ClickEvent<Button> buttonClickEvent) {
            log.info("Saving");
            selectedAction.setAlgorithm(editor.getValue());
            Result<List<Action>> result = actionService.saver().save(selectedAction);
            if (!result.isSuccess()){
                fireEvent(createNotificationEvent("action-editor.save-dialog.saving-fail"));
                fireEvent(createNotificationEvent(result.getSeed()));
            }
            closeDialog();
        }

        private void closeDialog() {
            saveDialog.close();
        }
    }

    private class SaveAsDialogHandler {
        private String description;

        public void handleCloseButtonClick(ClickEvent<Button> buttonClickEvent) {
            log.info("Close button is pressed");
            handleOnClosing();
        }

        public void handleSaveButtonClick(ClickEvent<Button> buttonClickEvent) {
            log.info("Save button is pressed");
            if (description != null && !description.isEmpty()){
                Action newAction = new Action();
                newAction.setDescription(description);
                newAction.setAlgorithm(editor.getValue());

                Result<List<Action>> result = actionService.saver().save(newAction);
                if (result.isSuccess()){
                    Action savedAction = result.getValue().get(0);
                    setSelectedAction(savedAction);
                    actionIdToUuidBinder.changeBinding(savedAction.getId(), id);
                } else {
                    fireEvent(createNotificationEvent("action-editor.save-as-dialog.saving-fail"));
                    fireEvent(createNotificationEvent(result.getSeed()));
                }
            } else {
                fireEvent(createNotificationEvent("action-editor.save-as-dialog.description-empty"));
            }
            handleOnClosing();
        }

        public void handleDescriptionChanging(AbstractField.ComponentValueChangeEvent<TextField, String> event) {
            log.info("Description changing: {} -> {}", event.getOldValue(), event.getValue());
            description = event.getValue();
        }

        public void handleClosing(Dialog.DialogCloseActionEvent dialogCloseActionEvent) {
            log.info("Closing");
            handleOnClosing();
        }

        private void handleOnClosing() {
            description = null;
            saveAsDialog.close();
        }
    }

    private class NewDialogHandler {
        private String description;

        public void handleCloseButtonClick(ClickEvent<Button> event) {
            log.info("Close button is pressed");
            handleOnClosing();
        }

        public void handleSaveButtonClick(ClickEvent<Button> event) {
            log.info("Close button is pressed");
            Optional<Seed> maybeSeed = checkDescription();
            if (maybeSeed.isEmpty()){
                Action newAction = new Action();
                newAction.setDescription(description);
                newAction.setAlgorithm(editor.getValue());
                Result<List<Action>> result = actionService.saver().save(newAction);
                if (result.isSuccess()){
                    setSelectedAction(result.getValue().get(0));
                    fireEvent(createNotificationEvent("action-editor.new-dialog.saved", NotificationType.INFO));
                } else {
                    fireEvent(createNotificationEvent("action-editor.new-dialog.saving-fail"));
                    fireEvent(createNotificationEvent(result.getSeed()));
                }
            } else {
                fireEvent(createNotificationEvent(maybeSeed.get()));
            }
            handleOnClosing();
        }

        public void handleDescriptionChanging(AbstractField.ComponentValueChangeEvent<TextField, String> event) {
            log.info("Description changing: {} -> {}", event.getOldValue(), event.getValue());
            description = event.getValue();
        }

        public void handleClosing(Dialog.DialogCloseActionEvent event) {
            log.info("Closing");
            handleOnClosing();
        }

        private void handleOnClosing() {
            description = null;
            newDialog.close();
        }

        private Optional<Seed> checkDescription() {
            return description == null || description.isBlank()
                    ? Optional.of(ImmutableSeed.builder().code("action-editor.new-dialog.empty-description").build())
                    : Optional.empty();
        }
    }
}
