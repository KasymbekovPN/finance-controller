package kpn.financecontroller.gui.views.tag.event;

import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.views.tag.controller.TagDomainController;

public final class TagDeleteReactionEvent extends DeleteFormEvent<TagDomainController, Tag> {
    public TagDeleteReactionEvent(TagDomainController source, Tag value) {
        super(source, value);
    }
}
