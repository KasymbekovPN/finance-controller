package kpn.financecontroller.gui.views.tag.event;

import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.views.tag.controller.TagDataController;

public final class TagDeleteReactionEvent extends DeleteFormEvent<TagDataController, Tag> {
    public TagDeleteReactionEvent(TagDataController source, Tag value) {
        super(source, value);
    }
}
