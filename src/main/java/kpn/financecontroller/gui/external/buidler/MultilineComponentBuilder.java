package kpn.financecontroller.gui.external.buidler;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextArea;
import kpn.financecontroller.annotation.External;

@External
public final class MultilineComponentBuilder implements ComponentBuilder {
    private final StringBuilder buffer = new StringBuilder();

    private String delimiter = "";

    public MultilineComponentBuilder append(String line){
        buffer.append(delimiter).append(line);
        delimiter = "\n";
        return this;
    }

    @Override
    public Component build() {
        Div div = new Div();
        div.add(createTextArea());
        return div;
    }

    private Component createTextArea() {
        TextArea area = new TextArea();
        area.setReadOnly(true);
        area.setValue(buffer.toString());
        return area;
    }
}
