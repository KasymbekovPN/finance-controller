package kpn.financecontroller.gui.external.builder;

import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.textfield.TextArea;
import kpn.financecontroller.annotation.External;

@External
public final class MultilineComponentBuilder implements ComponentBuilder<HasSize> {
    private final StringBuilder buffer = new StringBuilder();

    private String delimiter = "";

    public MultilineComponentBuilder append(String line){
        buffer.append(delimiter).append(line);
        delimiter = "\n";
        return this;
    }

    @Override
    public HasSize build() {
        TextArea area = new TextArea();
        area.setReadOnly(true);
        area.setValue(buffer.toString());
        return area;
    }
}
