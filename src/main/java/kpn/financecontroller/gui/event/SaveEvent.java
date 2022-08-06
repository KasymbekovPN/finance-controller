package kpn.financecontroller.gui.event;

import com.vaadin.flow.component.Component;

public abstract class SaveEvent<F extends Component, T> extends GuiEvent<F, T> {
    public SaveEvent(F source, T value) {
        super(source, value);
    }
}
