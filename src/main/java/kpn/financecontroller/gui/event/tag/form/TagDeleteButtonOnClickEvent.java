package kpn.financecontroller.gui.event.tag.form;

import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.gui.event.DeleteFormEvent;
import kpn.financecontroller.gui.form.TagForm;

public final class TagDeleteButtonOnClickEvent extends DeleteFormEvent<TagForm, Tag> {
    public TagDeleteButtonOnClickEvent(TagForm source, Tag value) {
        super(source, value);
    }
}
