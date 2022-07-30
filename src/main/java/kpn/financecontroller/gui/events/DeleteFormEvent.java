package kpn.financecontroller.gui.events;

import com.vaadin.flow.component.Component;

// TODO: 30.07.2022 rename - without form
public abstract class DeleteFormEvent<F extends Component, T> extends FormEvent<F, T> {
    public DeleteFormEvent(F source, T value) {
        super(source, value);
    }
}
