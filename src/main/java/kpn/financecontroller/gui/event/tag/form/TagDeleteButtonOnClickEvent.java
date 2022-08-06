package kpn.financecontroller.gui.event.tag.form;

import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.gui.event.DeleteEvent;
import kpn.financecontroller.gui.form.TagForm;

public final class TagDeleteButtonOnClickEvent extends DeleteEvent<TagForm, Tag> {
    public TagDeleteButtonOnClickEvent(TagForm source, Tag value) {
        super(source, value);
    }
}
