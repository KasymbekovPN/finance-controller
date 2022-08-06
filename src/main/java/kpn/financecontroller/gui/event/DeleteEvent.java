package kpn.financecontroller.gui.event;

import com.vaadin.flow.component.Component;

public abstract class DeleteEvent<F extends Component, T> extends GuiEvent<F, T> {
    public DeleteEvent(F source, T value) {
        super(source, value);
    }
}
