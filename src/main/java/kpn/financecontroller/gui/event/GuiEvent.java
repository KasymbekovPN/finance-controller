package kpn.financecontroller.gui.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import lombok.Getter;

@Getter
public abstract class GuiEvent<F extends Component, T> extends ComponentEvent<F> {
    private final T value;

    public GuiEvent(F source, T value) {
        super(source, false);
        this.value = value;
    }
}
