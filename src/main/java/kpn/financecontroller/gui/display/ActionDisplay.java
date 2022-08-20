package kpn.financecontroller.gui.display;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.shared.Registration;
import kpn.financecontroller.gui.event.action.display.ActionClearButtonOnClickEvent;
import kpn.financecontroller.gui.event.action.display.ActionRunButtonOnClickEvent;
import org.springframework.context.annotation.Scope;

@org.springframework.stereotype.Component
@Scope("prototype")
public final class ActionDisplay extends Div {
    private final Button run = new Button();
    private final Button clear = new Button();

    private Component currentContent;

    public ActionDisplay() {
        addClassName("action-display");
        currentContent = createDefaultContent();
        add(
                createButtons(),
                currentContent
        );
        setVisible(false);
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    private Component createButtons() {
        customizeRunButton();
        customizeClearButton();
        return new HorizontalLayout(run, clear);
    }

    private Component createDefaultContent() {
        TextArea textArea = new TextArea();
        textArea.setValue(getTranslation("gui.text.nothing"));
        textArea.setReadOnly(true);
        setComponentWidth(textArea);
        return textArea;
    }

    // TODO: 20.08.2022 remake
    private void setComponentWidth(HasSize component) {
        component.setWidth(25, Unit.EM);
    }

    private void customizeRunButton() {
        run.setText(getTranslation("gui.button.run"));
        run.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        run.addClickListener(event -> fireEvent(new ActionRunButtonOnClickEvent(this)));
    }

    private void customizeClearButton() {
        clear.setText(getTranslation("gui.button.clear"));
        clear.addThemeVariants(ButtonVariant.LUMO_ERROR);
        clear.addClickListener(event -> fireEvent(new ActionClearButtonOnClickEvent(this)));
    }

    public void handleRunEvent(ActionRunButtonOnClickEvent event) {
        System.out.println("handleRunEvent: " + event); // TODO: 20.08.2022 ???
        remove(currentContent);
        Div div = new Div(new TextArea("RUN"));
        setComponentWidth(div);
        currentContent = div;
        add(currentContent);
    }

    public void handleClearEvent(ActionClearButtonOnClickEvent event){
        System.out.println("handleClearEvent: " + event); // TODO: 20.08.2022 ???
        remove(currentContent);
        currentContent = createDefaultContent();
        add(currentContent);

    }
}
