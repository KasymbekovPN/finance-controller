package kpn.financecontroller.gui.event.tag.form;

import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.gui.event.SaveEvent;
import kpn.financecontroller.gui.form.TagForm;

public final class TagSaveButtonOnClickEvent extends SaveEvent<TagForm, Tag> {
    public TagSaveButtonOnClickEvent(TagForm source, Tag value) {
        super(source, value);
    }
}
