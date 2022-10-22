package kpn.financecontroller.gui.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.*;
import com.vaadin.flow.shared.Registration;
import kpn.financecontroller.gui.binding.util.Binder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import java.util.Optional;

@Slf4j
@Scope("prototype")
@Route(value = "display/:UUID?")
@PermitAll
public final class ActionDisplay extends VerticalLayout implements BeforeEnterObserver, AfterNavigationObserver, BeforeLeaveObserver {

    @Autowired
    @Qualifier("displayIdToActionIdBinder")
    private Binder<String, Long> displayIdToActionIdBinder;

    private String id;
    private boolean toHome;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> maybeId = event.getRouteParameters().get("UUID");
        log.info("maybe id: {}", maybeId);

        if (maybeId.isPresent()){
            id = maybeId.get();
            log.info("Key {} is set", id);
        } else {
            log.warn("[{}] key is empty", maybeId);
            toHome = true;
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        if (toHome){
            log.info("Navigate to home...");
            getUI().ifPresent(ui -> ui.navigate(PaymentView.class));
            // TODO: 22.10.2022 impl
//            fireEvent(createNotificationEvent("action-editor.uuid.not-unique"));
        }
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
        displayIdToActionIdBinder.unbind(id);
    }

    @Override
    protected <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    @PostConstruct
    private void init(){
        setSizeFull();
        add(getToolBarArea(), getDescriptionArea(), getContentArea());
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
        TextArea textArea = new TextArea();
        textArea.setReadOnly(true);
        textArea.setWidthFull();

        return textArea;
    }

    private Component getContentArea() {
        // TODO: 19.10.2022 impl it
        return new Div();
    }

    private void processHomeButtonClick() {
        log.info("Button home is clicked");
        getUI().ifPresent(ui -> ui.navigate(PaymentView.class));
    }

    private void processOpenButtonClick() {
        log.info("Button open is clicked");
        // TODO: 19.10.2022 impl
    }

    private void processClearButtonClick() {
        log.info("Button clear is clicked");
        // TODO: 19.10.2022 impl
    }

    private void processRunButtonClick() {
        log.info("Button run is clicked");
        // TODO: 19.10.2022 impl
    }
}
