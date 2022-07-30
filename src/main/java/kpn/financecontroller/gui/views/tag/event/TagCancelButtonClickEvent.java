package kpn.financecontroller.gui.views.tag.event;

import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.gui.events.CloseFormEvent;
import kpn.financecontroller.gui.views.tag.TagForm;

public final class TagCancelButtonClickEvent extends CloseFormEvent<TagForm, Tag> {
    public TagCancelButtonClickEvent(TagForm source) {
        super(source);
    }
}
