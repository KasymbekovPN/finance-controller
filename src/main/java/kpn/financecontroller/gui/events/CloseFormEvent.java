package kpn.financecontroller.gui.events;

import com.vaadin.flow.component.Component;

public abstract class CloseFormEvent<F extends Component, T> extends FormEvent<F, T> {
    public CloseFormEvent(F source) {
        super(source, null);
    }
}
