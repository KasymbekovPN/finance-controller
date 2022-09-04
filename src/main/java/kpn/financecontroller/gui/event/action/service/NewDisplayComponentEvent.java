package kpn.financecontroller.gui.event.action.service;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.HasSize;
import kpn.financecontroller.data.services.ActionProcessingService;
import lombok.Getter;

public final class NewDisplayComponentEvent extends ComponentEvent<ActionProcessingService> {
    @Getter
    private final HasSize component;

    public NewDisplayComponentEvent(ActionProcessingService source, HasSize component) {
        super(source, false);
        this.component = component;
    }
}
