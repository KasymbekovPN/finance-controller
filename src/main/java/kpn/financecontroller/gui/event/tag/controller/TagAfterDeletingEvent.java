package kpn.financecontroller.gui.event.tag.controller;

import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.gui.event.DeleteFormEvent;
import kpn.financecontroller.gui.controller.TagViewController;

public final class TagAfterDeletingEvent extends DeleteFormEvent<TagViewController, Tag> {
    public TagAfterDeletingEvent(TagViewController source, Tag value) {
        super(source, value);
    }
}
