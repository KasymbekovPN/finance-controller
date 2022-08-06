package kpn.financecontroller.gui.event.tag.controller;

import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.gui.event.SaveEvent;
import kpn.financecontroller.gui.controller.TagViewController;

public final class TagAfterSavingEvent extends SaveEvent<TagViewController, Tag> {
    public TagAfterSavingEvent(TagViewController source, Tag value) {
        super(source, value);
    }
}
