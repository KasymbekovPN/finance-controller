package kpn.financecontroller.gui.events;

import com.vaadin.flow.component.Component;

public abstract class SaveFormEvent<F extends Component, T> extends FormEvent<F, T> {
    public SaveFormEvent(F source, T value) {
        super(source, value);
    }
}
