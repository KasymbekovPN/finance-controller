package kpn.financecontroller.gui.event.action.service;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.HasSize;
import kpn.financecontroller.data.services.action.ActionProcessingServiceOld;
import lombok.Getter;

public final class NewDisplayComponentEvent extends ComponentEvent<ActionProcessingServiceOld> {
    @Getter
    private final HasSize component;

    public NewDisplayComponentEvent(ActionProcessingServiceOld source, HasSize component) {
        super(source, false);
        this.component = component;
    }
}
