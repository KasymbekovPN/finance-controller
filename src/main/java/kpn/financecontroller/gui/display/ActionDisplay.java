package kpn.financecontroller.gui.display;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import org.springframework.context.annotation.Scope;

@org.springframework.stereotype.Component
@Scope("prototype")
public final class ActionDisplay extends Div {
    private final Button run = new Button();
    private final Button clear = new Button();

    public ActionDisplay() {
        addClassName("action-display");
        add(
                createButtons(),
                createContent()
        );
        setVisible(false);
    }

    private Component createButtons() {
        customizeRunButton();
        customizeClearButton();
        return new HorizontalLayout(run, clear);
    }

    private Component createContent() {
        // TODO: 20.08.2022 create real content
        TextArea textArea = new TextArea();
        textArea.setValue("1234567890");
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
        // TODO: 20.08.2022 need event creation
//        cancel.addClickListener(event -> fireEvent(createCancelButtonOnClickEvent()));
    }

    private void customizeClearButton() {
        clear.setText(getTranslation("gui.button.clear"));
        run.addThemeVariants(ButtonVariant.LUMO_ERROR);
        // TODO: 20.08.2022 need event creation
//        cancel.addClickListener(event -> fireEvent(createCancelButtonOnClickEvent()));
    }
}
