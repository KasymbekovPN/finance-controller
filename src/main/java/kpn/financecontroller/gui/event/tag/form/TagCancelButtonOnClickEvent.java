package kpn.financecontroller.gui.event.tag.form;

import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.gui.event.CloseFormEvent;
import kpn.financecontroller.gui.form.TagForm;

public final class TagCancelButtonOnClickEvent extends CloseFormEvent<TagForm, Tag> {
    public TagCancelButtonOnClickEvent(TagForm source) {
        super(source);
    }
}
