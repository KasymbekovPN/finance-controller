package kpn.financecontroller.gui.form;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import kpn.financecontroller.data.domain.Tag;
import kpn.financecontroller.gui.event.tag.form.TagCancelButtonOnClickEvent;
import kpn.financecontroller.gui.event.tag.form.TagDeleteButtonOnClickEvent;
import kpn.financecontroller.gui.event.tag.form.TagSaveButtonOnClickEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public final class TagForm extends Form<Tag> {
    private final TextField name = new TextField();

    public TagForm() {
        super(new Binder<>(Tag.class));
        addClassName("tag-form");
        binder.bindInstanceFields(this);

        name.setLabel(getTranslation("gui.label.name"));
        name.setPlaceholder(getTranslation("gui.placeholder.type-name"));

        add(
                name,
                createButtonsLayout()
        );

        setWidth("25em");
        close(true);
    }

    @Override
    protected ComponentEvent<?> createSaveButtonOnClickEvent() {
        return new TagSaveButtonOnClickEvent(this, value);
    }

    @Override
    protected ComponentEvent<?> createCancelButtonOnClickEvent() {
        return new TagCancelButtonOnClickEvent(this);
    }

    @Override
    protected ComponentEvent<?> createDeleteButtonOnClickEvent() {
        return new TagDeleteButtonOnClickEvent(this, value);
    }
}
