package kpn.financecontroller.gui.events;

import com.vaadin.flow.component.Component;

public abstract class DeleteFormEvent<F extends Component, T> extends FormEvent<F, T> {
    public DeleteFormEvent(F source, T value) {
        super(source, value);
    }
}
