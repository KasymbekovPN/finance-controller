package kpn.financecontroller.gui.display;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextArea;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public final class ActionDisplay extends Div {

    public ActionDisplay() {
        addClassName("action-display");

        TextArea textArea = new TextArea();
        textArea.setValue("1234567890");

        add(textArea);

        setVisible(false);
    }
}
