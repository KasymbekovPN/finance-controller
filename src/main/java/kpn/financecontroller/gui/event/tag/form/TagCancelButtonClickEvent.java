package kpn.financecontroller.gui.event.tag.form;

import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.gui.event.CloseFormEvent;
import kpn.financecontroller.gui.form.TagForm;

public final class TagCancelButtonClickEvent extends CloseFormEvent<TagForm, Tag> {
    public TagCancelButtonClickEvent(TagForm source) {
        super(source);
    }
}
