package kpn.financecontroller.gui.binding.event;

import kpn.financecontroller.data.domain.Action;
import kpn.financecontroller.gui.controller.ActionViewController;
import kpn.financecontroller.gui.event.action.controller.ActionAfterDeletingEvent;
import kpn.financecontroller.gui.event.action.controller.ActionAfterSavingEvent;
import kpn.financecontroller.gui.event.action.controller.ActionControllerNotificationEvent;
import kpn.financecontroller.gui.event.action.form.ActionCancelButtonOnClickEvent;
import kpn.financecontroller.gui.event.action.form.ActionDeleteButtonOnClickEvent;
import kpn.financecontroller.gui.event.action.form.ActionSaveButtonOnClickEvent;
import kpn.financecontroller.gui.event.action.view.ActionViewNotificationEvent;
import kpn.financecontroller.gui.form.ActionForm;
import kpn.financecontroller.gui.view.ActionView;
import org.springframework.stereotype.Component;

@Component
public final class ActionEventBindingBPP extends DomainEventBindingBPP<Action> {

    @Override
    protected void doBinding() {
        form.addListener(ActionSaveButtonOnClickEvent.class, viewController::handleSavingEvent);
        viewController.addListener(ActionAfterSavingEvent.class, gridView::handleSavingEvent);
        form.addListener(ActionDeleteButtonOnClickEvent.class, viewController::handleDeletingEvent);
        viewController.addListener(ActionAfterDeletingEvent.class, gridView::handleDeletingEvent);
        form.addListener(ActionCancelButtonOnClickEvent.class, gridView::handleCancelEvent);
        viewController.addListener(ActionControllerNotificationEvent.class, notificationService::notify);
        gridView.addListener(ActionViewNotificationEvent.class, notificationService::notify);
    }

    @Override
    protected ChainLink createChainLink() {
        return super.createChainLink()
                .addNext(createViewControllerChainLink(ActionViewController.class))
                .addNext(createGridViewChainLink(ActionView.class))
                .addNext(createFormChainLink(ActionForm.class));
    }
}
