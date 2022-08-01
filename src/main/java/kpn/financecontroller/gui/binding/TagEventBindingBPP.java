package kpn.financecontroller.gui.binding;

import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.gui.controller.TagViewController;
import kpn.financecontroller.gui.event.tag.controller.TagAfterDeletingEvent;
import kpn.financecontroller.gui.event.tag.controller.TagAfterSavingEvent;
import kpn.financecontroller.gui.event.tag.controller.TagControllerNotificationEvent;
import kpn.financecontroller.gui.event.tag.form.TagCancelButtonClickEvent;
import kpn.financecontroller.gui.event.tag.form.TagDeleteButtonOnClickEvent;
import kpn.financecontroller.gui.event.tag.form.TagSaveButtonOnClickEvent;
import kpn.financecontroller.gui.event.tag.view.TagViewNotificationEvent;
import kpn.financecontroller.gui.form.TagForm;
import kpn.financecontroller.gui.notifications.NotificationServiceImpl;
import kpn.financecontroller.gui.view.TagView;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public final class TagEventBindingBPP extends EventBindingBPP<Tag> {

    @Override
    protected boolean checkBindingCondition() {
        return form != null && gridView != null && viewController != null;
    }

    @Override
    protected void doBinding() {
        form.addListener(TagSaveButtonOnClickEvent.class, viewController::handleSavingEvent);
        viewController.addListener(TagAfterSavingEvent.class, gridView::handleSavingEvent);
        form.addListener(TagDeleteButtonOnClickEvent.class, viewController::handleDeletingEvent);
        viewController.addListener(TagAfterDeletingEvent.class, gridView::handleDeletingEvent);
        form.addListener(TagCancelButtonClickEvent.class, gridView::handleCancelEvent);
        viewController.addListener(TagControllerNotificationEvent.class, notificationService::notify);
        gridView.addListener(TagViewNotificationEvent.class, notificationService::notify);
    }

    @Override
    protected ChainLink createChainLink() {
        return new ChainLink(createNotificationServiceChainLink())
                .addNext(createViewControllerChainLink())
                .addNext(createFormChainLink())
                .addNext(createGridViewChainLink());
    }

    private Function<Object, Boolean> createNotificationServiceChainLink() {
        return  (Object bean) -> {
            if (bean.getClass().equals(NotificationServiceImpl.class)){
                notificationService = (NotificationServiceImpl) bean;
                return true;
            }
            return false;
        };
    }

    private Function<Object, Boolean> createViewControllerChainLink() {
        return  (Object bean) -> {
            if (bean.getClass().equals(TagViewController.class)){
                viewController = (TagViewController) bean;
                return true;
            }
            return false;
        };
    }

    private Function<Object, Boolean> createFormChainLink() {
        return  (Object bean) -> {
            if (bean.getClass().equals(TagForm.class)){
                form = (TagForm) bean;
                return true;
            }
            return false;
        };
    }

    private Function<Object, Boolean> createGridViewChainLink() {
        return  (Object bean) -> {
            if (bean.getClass().equals(TagView.class)){
                gridView = (TagView) bean;
                return true;
            }
            return false;
        };
    }
}