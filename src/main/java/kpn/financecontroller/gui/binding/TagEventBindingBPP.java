package kpn.financecontroller.gui.binding;

import kpn.financecontroller.data.domain.Tag;
import kpn.financecontroller.gui.controller.TagViewController;
import kpn.financecontroller.gui.event.tag.controller.TagAfterDeletingEvent;
import kpn.financecontroller.gui.event.tag.controller.TagAfterSavingEvent;
import kpn.financecontroller.gui.event.tag.controller.TagControllerNotificationEvent;
import kpn.financecontroller.gui.event.tag.form.TagCancelButtonOnClickEvent;
import kpn.financecontroller.gui.event.tag.form.TagDeleteButtonOnClickEvent;
import kpn.financecontroller.gui.event.tag.form.TagSaveButtonOnClickEvent;
import kpn.financecontroller.gui.event.tag.view.TagViewNotificationEvent;
import kpn.financecontroller.gui.form.TagForm;
import kpn.financecontroller.gui.notifications.NotificationServiceImpl;
import kpn.financecontroller.gui.view.TagView;
import org.springframework.stereotype.Component;

@Component
public final class TagEventBindingBPP extends EventBindingBPP<Tag> {

    @Override
    protected void doBinding() {
        form.addListener(TagSaveButtonOnClickEvent.class, viewController::handleSavingEvent);
        viewController.addListener(TagAfterSavingEvent.class, gridView::handleSavingEvent);
        form.addListener(TagDeleteButtonOnClickEvent.class, viewController::handleDeletingEvent);
        viewController.addListener(TagAfterDeletingEvent.class, gridView::handleDeletingEvent);
        form.addListener(TagCancelButtonOnClickEvent.class, gridView::handleCancelEvent);
        viewController.addListener(TagControllerNotificationEvent.class, notificationService::notify);
        gridView.addListener(TagViewNotificationEvent.class, notificationService::notify);
    }

    @Override
    protected ChainLink createChainLink() {
        return new ChainLink(createNotificationServiceChainLink1(NotificationServiceImpl.class))
                .addNext(createViewControllerChainLink1(TagViewController.class))
                .addNext(createFormChainLink1(TagForm.class))
                .addNext(createGridViewChainLink1(TagView.class));
    }
}