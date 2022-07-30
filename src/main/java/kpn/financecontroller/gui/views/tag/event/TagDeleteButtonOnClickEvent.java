package kpn.financecontroller.gui.views.tag.event;

import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.views.tag.TagForm;

public final class TagDeleteButtonOnClickEvent extends DeleteFormEvent<TagForm, Tag> {
    public TagDeleteButtonOnClickEvent(TagForm source, Tag value) {
        super(source, value);
    }
}
