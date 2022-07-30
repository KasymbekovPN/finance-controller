package kpn.financecontroller.gui.views.tag.event;

import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.views.tag.controller.TagDataController;

// TODO: 30.07.2022 rename
public final class TagSaveReactionEvent extends SaveFormEvent<TagDataController, Tag> {
    public TagSaveReactionEvent(TagDataController source, Tag value) {
        super(source, value);
    }
}
