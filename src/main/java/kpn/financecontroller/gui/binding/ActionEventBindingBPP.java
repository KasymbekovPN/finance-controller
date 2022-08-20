package kpn.financecontroller.gui.binding;

import kpn.financecontroller.data.domain.Action;
import kpn.financecontroller.gui.controller.ActionViewController;
import kpn.financecontroller.gui.display.ActionDisplay;
import kpn.financecontroller.gui.event.action.controller.ActionAfterDeletingEvent;
import kpn.financecontroller.gui.event.action.controller.ActionAfterSavingEvent;
import kpn.financecontroller.gui.event.action.controller.ActionControllerNotificationEvent;
import kpn.financecontroller.gui.event.action.display.ActionClearButtonOnClickEvent;
import kpn.financecontroller.gui.event.action.display.ActionRunButtonOnClickEvent;
import kpn.financecontroller.gui.event.action.form.ActionCancelButtonOnClickEvent;
import kpn.financecontroller.gui.event.action.form.ActionDeleteButtonOnClickEvent;
import kpn.financecontroller.gui.event.action.form.ActionSaveButtonOnClickEvent;
import kpn.financecontroller.gui.event.action.view.ActionViewNotificationEvent;
import kpn.financecontroller.gui.form.ActionForm;
import kpn.financecontroller.gui.notifications.NotificationServiceImpl;
import kpn.financecontroller.gui.view.ActionView;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public final class ActionEventBindingBPP extends EventBindingBPP<Action> {
    private ActionDisplay display;

    @Override
    protected void doBinding() {
        form.addListener(ActionSaveButtonOnClickEvent.class, viewController::handleSavingEvent);
        viewController.addListener(ActionAfterSavingEvent.class, gridView::handleSavingEvent);
        form.addListener(ActionDeleteButtonOnClickEvent.class, viewController::handleDeletingEvent);
        viewController.addListener(ActionAfterDeletingEvent.class, gridView::handleDeletingEvent);
        form.addListener(ActionCancelButtonOnClickEvent.class, gridView::handleCancelEvent);
        viewController.addListener(ActionControllerNotificationEvent.class, notificationService::notify);
        gridView.addListener(ActionViewNotificationEvent.class, notificationService::notify);
        display.addListener(ActionRunButtonOnClickEvent.class, display::handleRunEvent);
        display.addListener(ActionClearButtonOnClickEvent.class, display::handleClearEvent);
    }

    @Override
    protected ChainLink createChainLink() {
        return new ChainLink(createNotificationServiceChainLink(NotificationServiceImpl.class))
                .addNext(createViewControllerChainLink(ActionViewController.class))
                .addNext(createFormChainLink(ActionForm.class))
                .addNext(createGridViewChainLink(ActionView.class))
                .addNext(createDisplayChainLink());
    }

    private Function<Object, Boolean> createDisplayChainLink() {
        return (Object bean) -> {
            if (bean.getClass().equals(ActionDisplay.class)){
                display = (ActionDisplay) bean;
                return true;
            }
            return false;
        };
    }
}
