package kpn.financecontroller.gui.view;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.shared.Registration;
import kpn.financecontroller.data.domain.Action;
import kpn.financecontroller.data.services.FutureInterfaceService;
import kpn.financecontroller.data.services.dto.service.ActionDtoDecorator;
import kpn.financecontroller.gui.binding.util.Binder;
import kpn.financecontroller.gui.dialog.OpenActionDialog;
import kpn.financecontroller.gui.event.action.display.ActionDisplayNotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.lib.result.Result;
import kpn.lib.seed.ImmutableSeed;
import kpn.lib.seed.Seed;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Scope("prototype")
@Route(value = "display/:UUID?")
@PermitAll
@ConfigurationProperties(prefix = "action.display.execution")
public final class ActionDisplay extends VerticalLayout implements BeforeEnterObserver, AfterNavigationObserver, BeforeLeaveObserver {

    private final OpenDialogHandler openDialogHandler = new OpenDialogHandler();

    private final OpenActionDialog openDialog = new OpenActionDialog();

    @Setter
    private ExecutionTimeoutParam timeoutParams = new ExecutionTimeoutParam();

    @Autowired
    private ActionDtoDecorator actionService;
    @Autowired
    @Qualifier("displayIdToActionIdBinder")
    private Binder<String, Long> displayIdToActionIdBinder;

    @Autowired
    private FutureInterfaceService<Action, Result<Component>> actionExecutionService;

    private String id;
    private boolean toHome;
    private Long selectedActionId = null;
    private TextArea descriptionArea;
    private Div contentArea;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> maybeId = event.getRouteParameters().get("UUID");
        log.info("maybe id: {}", maybeId);

        if (maybeId.isPresent()){
            id = maybeId.get();
            log.info("Key {} is set", id);

            Optional<Long> maybeActionId = displayIdToActionIdBinder.get(id);
            maybeActionId.ifPresent(id -> {
                Result<List<Action>> result = actionService.loader().byId(id);
                if (result.isSuccess()) {
                    setSelectedAction(result.getValue().get(0));
                } else {
                    fireEvent(createNotificationEvent("action-display.action.transferred-bad-id"));
                    fireEvent(createNotificationEvent(result.getSeed()));
                }
            });
        } else {
            log.warn("[{}] key is empty", maybeId);
            toHome = true;
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        if (toHome){
            log.warn("Navigate to home...");
            getUI().ifPresent(ui -> ui.navigate(PaymentView.class));
            fireEvent(createNotificationEvent("action-display.uuid.empty"));
        }
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
        log.info("leaving...");
        displayIdToActionIdBinder.unbind(id);
        selectedActionId = null;
    }

    @Override
    public  <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    @PostConstruct
    private void init(){
        setSizeFull();
        changeContent(createEmptyContent());
        add(getToolBarArea(), getDescriptionArea(), contentArea);
        configOpenDialog();
    }

    private Component getToolBarArea() {
        Button homeButton = new Button(getTranslation("gui.button.home"));
        homeButton.addClickListener(e -> processHomeButtonClick());
        Button openButton = new Button(getTranslation("gui.button.open"));
        openButton.addClickListener(e -> processOpenButtonClick());
        Button clearButton = new Button(getTranslation("gui.button.clear"));
        clearButton.addClickListener(e -> processClearButtonClick());
        Button runButton = new Button(getTranslation("gui.button.run"));
        runButton.addClickListener(e -> processRunButtonClick());

        return new HorizontalLayout(
                homeButton,
                openButton,
                clearButton,
                runButton
        );
    }

    private Component getDescriptionArea() {
        descriptionArea = new TextArea();
        descriptionArea.setReadOnly(true);
        descriptionArea.setWidthFull();

        return descriptionArea;
    }

    private void setSelectedAction(Action action) {
        selectedActionId = action.getId();
        displayIdToActionIdBinder.changeBinding(ActionDisplay.this.id, selectedActionId);
        setDescription(action);
    }

    private void setDescription(Action action){
        if (action != null && action.getDescription() != null){
            descriptionArea.setValue(action.getDescription());
        }
    }

    private void configOpenDialog() {
        openDialog.addCancelButtonClickListener(openDialogHandler::handleCloseButtonClick);
        openDialog.addOpenButtonClickListener(openDialogHandler::handleOpenButtonClick);
        openDialog.addFilterValueChangeListener(openDialogHandler::handleFilterChanging);
        openDialog.addListValueChangeListener(openDialogHandler::handleListChanging);
    }

    private void processHomeButtonClick() {
        log.info("Button home is clicked");
        getUI().ifPresent(ui -> ui.navigate(PaymentView.class));
        displayIdToActionIdBinder.unbind(id);
    }

    private void processOpenButtonClick() {
        log.info("Button open is clicked");
        Result<List<Action>> result = actionService.loader().all();
        if (result.isSuccess()){
            openDialog.setItems(result.getValue());
            openDialog.open();
        } else {
            fireEvent(createNotificationEvent("action-display.click-open-button.fail"));
            fireEvent(createNotificationEvent(result.getSeed()));
        }
    }

    private void processClearButtonClick() {
        log.info("Button clear is clicked");
        changeContent(createEmptyContent());
    }

    private void processRunButtonClick() {
        log.info("Button run is clicked");
        Result<List<Action>> loadingResult = actionService.loader().byId(selectedActionId);
        if (loadingResult.isSuccess()){
            Future<Result<Component>> future = actionExecutionService.calculate(loadingResult.getValue().get(0));
            try {
                Result<Component> calculationResult = future.get(timeoutParams.getTimeout(), timeoutParams.getUnit());
                if (calculationResult.isSuccess()){
                    changeContent(calculationResult.getValue());
                } else {
                    changeContent(createContentBySeed(calculationResult.getSeed()));
                }
            } catch (InterruptedException | ExecutionException e) {
                changeContent(createContentByCode("action-display.execution.exception"));
            } catch (TimeoutException e) {
                changeContent(createContentByCode("action-display.execution.timeout"));
            }
        } else {
            changeContent(createContentByCode("action-display.execution.action-absent"));
        }
    }

    private void changeContent(Component content) {
        if (contentArea != null){
            remove(contentArea);
        }
        contentArea = new Div(content);
        contentArea.setSizeFull();
        add(contentArea);
    }

    private Component createEmptyContent() {
        return createContentByCode("gui.text.nothing");
    }

    private Component createContentBySeed(Seed seed){
        return createContentByCode(seed.getCode(), seed.getArgs());
    }

    private Component createContentByCode(String code, Object... params){
        TextArea text = new TextArea();
        text.setValue(getTranslation(code, params));
        text.setReadOnly(true);
        text.setSizeFull();
        return text;
    }

    private ComponentEvent<?> createNotificationEvent(String code) {
        return new ActionDisplayNotificationEvent(this, ImmutableSeed.builder().code(code).build(), NotificationType.ERROR);
    }

    private ActionDisplayNotificationEvent createNotificationEvent(Seed seed){
        return new ActionDisplayNotificationEvent(this, seed, NotificationType.ERROR);
    }

    private class OpenDialogHandler {
        private Action action;

        public void handleCloseButtonClick(ClickEvent<Button> event) {
            log.info("closing");
            handleClosing();
        }

        public void handleOpenButtonClick(ClickEvent<Button> event) {
            log.info("opening");
            if (action != null){
                setSelectedAction(action);
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

        private void handleClosing(){
            action = null;
            openDialog.close();
        }
    }

    @Setter
    private static class ExecutionTimeoutParam {
        private static final long DEFAULT_TIMEOUT = 10;
        private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;

        private Long timeout;
        private String unit;
        private boolean isValid;
        private TimeUnit timeUnit;

        public Long getTimeout() {
            if (!isValid){
                validate();
            }
            return timeout;
        }

        public TimeUnit getUnit() {
            if (!isValid){
                validate();
            }
            return timeUnit;
        }

        private void validate() {
            if (timeout == null){
                applyDefaultValues();
            } else if (unit == null){
                applyDefaultValues();
            } else {
                try {
                    timeUnit = TimeUnit.valueOf(unit);
                } catch (IllegalArgumentException ignored){
                    applyDefaultValues();
                }
            }
            isValid = true;
        }

        private void applyDefaultValues() {
            timeout = DEFAULT_TIMEOUT;
            timeUnit = DEFAULT_TIME_UNIT;
        }
    }
}
