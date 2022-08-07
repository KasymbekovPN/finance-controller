package kpn.financecontroller.gui.event.tag.form;

import kpn.financecontroller.data.domain.Tag;
import kpn.financecontroller.gui.event.CancelEvent;
import kpn.financecontroller.gui.form.TagForm;

public final class TagCancelButtonOnClickEvent extends CancelEvent<TagForm, Tag> {
    public TagCancelButtonOnClickEvent(TagForm source) {
        super(source);
    }
}
