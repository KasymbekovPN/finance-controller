package kpn.financecontroller.gui.form;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import kpn.financecontroller.data.domain.Action;
import kpn.financecontroller.gui.event.action.form.ActionCancelButtonOnClickEvent;
import kpn.financecontroller.gui.event.action.form.ActionDeleteButtonOnClickEvent;
import kpn.financecontroller.gui.event.action.form.ActionSaveButtonOnClickEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public final class ActionForm extends Form<Action> {
    private final TextField description = new TextField();
    private final TextArea algorithm = new TextArea();

    // TODO: 15.08.2022 del
    private final VerticalLayout layout = new VerticalLayout();

    public ActionForm() {
        super(new Binder<>(Action.class));
        addClassName("action-form");
        binder.bindInstanceFields(this);

        description.setLabel(getTranslation("gui.label.description"));
        description.setPlaceholder(getTranslation("gui.placeholder.type-description"));
        description.setWidthFull();

        algorithm.setLabel(getTranslation("gui.label.algorithm"));
        algorithm.setPlaceholder(getTranslation("gui.placeholder.type-algorithm"));

        layout.addAndExpand(createButtonsLayout(), description, algorithm);
        layout.setSizeFull();
        description.setWidth("100%");
        algorithm.setSizeFull();

        add(layout);

        setWidth(49, Unit.PERCENTAGE);
//        setSizeFull(); // TODO: 20.08.2022 ???
        close(true);
    }

    @Override
    protected void customizeSaveButton() {
        save.setText(getTranslation("gui.button.save"));
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> processSaveButtonClick());
        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));
    }

    @Override
    protected ComponentEvent<?> createSaveButtonOnClickEvent() {
        return new ActionSaveButtonOnClickEvent(this, value);
    }

    @Override
    protected ComponentEvent<?> createCancelButtonOnClickEvent() {
        return new ActionCancelButtonOnClickEvent(this);
    }

    @Override
    protected ComponentEvent<?> createDeleteButtonOnClickEvent() {
        return new ActionDeleteButtonOnClickEvent(this, value);
    }
}
