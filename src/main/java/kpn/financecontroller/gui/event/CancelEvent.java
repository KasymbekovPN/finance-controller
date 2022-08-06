package kpn.financecontroller.gui.event;

import com.vaadin.flow.component.Component;

public abstract class CancelEvent<F extends Component, T> extends GuiEvent<F, T> {
    public CancelEvent(F source) {
        super(source, null);
    }
}
