package kpn.financecontroller.gui.views.tag;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.gui.events.CloseFormEvent;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.views.EditForm;

public class TagForm extends EditForm<Tag> {

    private final TextField name = new TextField("Name", "type name...");

    public TagForm() {
        super(new Binder<>(Tag.class));
        addClassName("tag-form");
        binder.bindInstanceFields(this);

        add(
                name,
                createButtonsLayout()
        );
    }

    @Override
    protected SaveFormEvent<EditForm<Tag>, Tag> createSaveEvent() {
        return new TagSaveFormEvent(this, value);
    }

    @Override
    protected DeleteFormEvent<EditForm<Tag>, Tag> createDeleteEvent() {
        return new TagDeleteFormEvent(this, value);
    }

    @Override
    protected CloseFormEvent<EditForm<Tag>, Tag> createCloseEvent() {
        return new TagCloseFormEvent(this);
    }

    public static class TagSaveFormEvent extends SaveFormEvent<EditForm<Tag>, Tag> {
        public TagSaveFormEvent(EditForm<Tag> source, Tag value) {
            super(source, value);
        }
    }

    public static class TagDeleteFormEvent extends DeleteFormEvent<EditForm<Tag>, Tag> {
        public TagDeleteFormEvent(EditForm<Tag> source, Tag value) {
            super(source, value);
        }
    }

    public static class TagCloseFormEvent extends CloseFormEvent<EditForm<Tag>, Tag> {
        public TagCloseFormEvent(EditForm<Tag> source) {
            super(source);
        }
    }
}
