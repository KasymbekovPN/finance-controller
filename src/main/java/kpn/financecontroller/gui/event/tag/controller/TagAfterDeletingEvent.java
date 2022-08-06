package kpn.financecontroller.gui.event.tag.controller;

import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.gui.event.DeleteEvent;
import kpn.financecontroller.gui.controller.TagViewController;

public final class TagAfterDeletingEvent extends DeleteEvent<TagViewController, Tag> {
    public TagAfterDeletingEvent(TagViewController source, Tag value) {
        super(source, value);
    }
}
