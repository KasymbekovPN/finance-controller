package kpn.financecontroller.gui.views.tag.event;

import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.views.tag.TagForm;

public final class TagSaveButtonOnClickEvent extends SaveFormEvent<TagForm, Tag> {
    public TagSaveButtonOnClickEvent(TagForm source, Tag value) {
        super(source, value);
    }
}
