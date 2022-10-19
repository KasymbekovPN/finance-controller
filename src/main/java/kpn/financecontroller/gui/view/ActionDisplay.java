package kpn.financecontroller.gui.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;

@Slf4j
@Scope("prototype")
@Route(value = "display/:UUID?")
@PermitAll
public final class ActionDisplay extends VerticalLayout implements BeforeEnterObserver {

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // TODO: 17.10.2022 impl
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
        // TODO: 19.10.2022 add button to action editor
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
        // TODO: 19.10.2022 impl
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
