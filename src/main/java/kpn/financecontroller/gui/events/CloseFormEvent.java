package kpn.financecontroller.gui.events;

import com.vaadin.flow.component.Component;

// TODO: 30.07.2022 rename - without form + close to cancel
public abstract class CloseFormEvent<F extends Component, T> extends FormEvent<F, T> {
    public CloseFormEvent(F source) {
        super(source, null);
    }
}
