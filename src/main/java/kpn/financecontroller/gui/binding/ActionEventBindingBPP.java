package kpn.financecontroller.gui.binding;

import kpn.financecontroller.data.domain.Action;
import kpn.financecontroller.data.services.ActionProcessingService;
import kpn.financecontroller.gui.controller.ActionViewController;
import kpn.financecontroller.gui.display.ActionDisplay;
import kpn.financecontroller.gui.event.action.controller.ActionAfterDeletingEvent;
import kpn.financecontroller.gui.event.action.controller.ActionAfterSavingEvent;
import kpn.financecontroller.gui.event.action.controller.ActionControllerNotificationEvent;
import kpn.financecontroller.gui.event.action.display.ActionStartForFormEvent;
import kpn.financecontroller.gui.event.action.display.ActionStartForProcessorEvent;
import kpn.financecontroller.gui.event.action.form.ActionCancelButtonOnClickEvent;
import kpn.financecontroller.gui.event.action.form.ActionDeleteButtonOnClickEvent;
import kpn.financecontroller.gui.event.action.form.ActionSaveButtonOnClickEvent;
import kpn.financecontroller.gui.event.action.service.NewDisplayComponentEvent;
import kpn.financecontroller.gui.event.action.view.ActionViewNotificationEvent;
import kpn.financecontroller.gui.form.ActionForm;
import kpn.financecontroller.gui.notifications.NotificationServiceImpl;
import kpn.financecontroller.gui.view.ActionView;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public final class ActionEventBindingBPP extends EventBindingBPP<Action> {
    private ActionDisplay display;
    private ActionProcessingService actionService;
    private ActionForm actionForm;

    @Override
    protected void doBinding() {
        actionForm.addListener(ActionSaveButtonOnClickEvent.class, viewController::handleSavingEvent);
        viewController.addListener(ActionAfterSavingEvent.class, gridView::handleSavingEvent);
        actionForm.addListener(ActionDeleteButtonOnClickEvent.class, viewController::handleDeletingEvent);
        viewController.addListener(ActionAfterDeletingEvent.class, gridView::handleDeletingEvent);
        actionForm.addListener(ActionCancelButtonOnClickEvent.class, gridView::handleCancelEvent);
        viewController.addListener(ActionControllerNotificationEvent.class, notificationService::notify);
        gridView.addListener(ActionViewNotificationEvent.class, notificationService::notify);
        display.addListener(ActionStartForFormEvent.class, actionForm::handleActionStart);
        actionForm.addListener(ActionStartForProcessorEvent.class, actionService::handleActionStart);
        actionService.addListener(NewDisplayComponentEvent.class, display::handleNewDisplayEvent);
    }

    @Override
    protected ChainLink createChainLink() {
        return new ChainLink(createNotificationServiceChainLink(NotificationServiceImpl.class))
                .addNext(createViewControllerChainLink(ActionViewController.class))
                .addNext(createGridViewChainLink(ActionView.class))
                .addNext(createDisplayChainLink())
                .addNext(createActionServiceChainLink())
                .addNext(createActionFormChainLink());
    }

    @Override
    protected void reset() {
        super.reset();
        display = null;
        actionService = null;
    }

    @Override
    protected boolean checkBindingCondition() {
        return notificationService != null &&
                viewController != null &&
                gridView != null &&
                display != null &&
                actionService != null &&
                actionForm != null;
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

    private Function<Object, Boolean> createActionServiceChainLink() {
        return (Object bean) -> {
            if (bean.getClass().equals(ActionProcessingService.class)){
                actionService = (ActionProcessingService) bean;
                return true;
            }
            return false;
        };
    }

    private Function<Object, Boolean> createActionFormChainLink() {
        return (Object bean) -> {
            if (bean.getClass().equals(ActionForm.class)){
                actionForm = (ActionForm) bean;
                return true;
            }
            return false;
        };
    }
}
