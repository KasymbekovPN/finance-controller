package kpn.financecontroller.gui.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.*;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import java.util.Optional;

// TODO: 24.09.2022 add disconnect button which disconnect connection between algorithm and uuid
@Scope("prototype")
@Route(value = "editor/:UUID?")
@PermitAll
public final class ActionEditor extends VerticalLayout implements BeforeEnterObserver, BeforeLeaveObserver {

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // TODO: 21.09.2022 ???
        Optional<String> id = event.getRouteParameters().get("UUID");
        System.out.println("id: " + id);
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
        System.out.println("leaving");
    }

    @PostConstruct
    private void init(){
        setSizeFull();
        add(getToolBar(), getTextArea());
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

        HorizontalLayout toolbar = new HorizontalLayout(
                openButton,
                saveButton,
                saveAsButton,
                runButton
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

    private void processOpenButtonClick() {
        // TODO: 19.09.2022 impl
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
}
