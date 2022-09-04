package kpn.financecontroller.gui.display;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.shared.Registration;
import kpn.financecontroller.gui.event.action.display.ActionStartForFormEvent;
import kpn.financecontroller.gui.event.action.service.NewDisplayComponentEvent;
import org.springframework.context.annotation.Scope;

@org.springframework.stereotype.Component
@Scope("prototype")
public final class ActionDisplay extends Div {
    private final Button run = new Button();
    private final Button clear = new Button();

    private Div currentContent;

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

    private Div createDefaultContent() {
        TextArea textArea = new TextArea();
        textArea.setValue(getTranslation("gui.text.nothing"));
        textArea.setReadOnly(true);
        setComponentWidth(textArea);
        return new Div(textArea);
    }

    // TODO: 20.08.2022 remake
    private void setComponentWidth(HasSize component) {
        component.setWidth(25, Unit.EM);
    }

    private void customizeRunButton() {
        run.setText(getTranslation("gui.button.run"));
        run.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        run.addClickListener(event -> handleRunPushing());
    }

    private void customizeClearButton() {
        clear.setText(getTranslation("gui.button.clear"));
        clear.addThemeVariants(ButtonVariant.LUMO_ERROR);
        clear.addClickListener(event -> handleClearPushing());
    }

    private void handleRunPushing() {
        fireEvent(new ActionStartForFormEvent(this));
    }

    private void handleClearPushing() {
        remove(currentContent);
        currentContent = createDefaultContent();
        add(currentContent);
    }

    public void handleNewDisplayEvent(NewDisplayComponentEvent event) {
        HasSize newComponent = event.getComponent();
        setComponentWidth(newComponent);
        remove(currentContent);
        currentContent = new Div();
        currentContent.add((Component) newComponent);
        add(currentContent);
    }
}
