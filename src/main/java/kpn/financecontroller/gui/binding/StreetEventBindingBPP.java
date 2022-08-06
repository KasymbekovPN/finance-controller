package kpn.financecontroller.gui.binding;

import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.gui.controller.StreetViewController;
import kpn.financecontroller.gui.event.street.controller.StreetAfterDeletingEventOnClickEvent;
import kpn.financecontroller.gui.event.street.controller.StreetAfterSavingEventOnClickEvent;
import kpn.financecontroller.gui.event.street.controller.StreetControllerNotificationEvent;
import kpn.financecontroller.gui.event.street.form.StreetCancelButtonOnClickEvent;
import kpn.financecontroller.gui.event.street.form.StreetDeleteButtonOnClickEvent;
import kpn.financecontroller.gui.event.street.form.StreetSaveButtonOnClickEvent;
import kpn.financecontroller.gui.event.street.view.StreetViewNotificationEvent;
import kpn.financecontroller.gui.form.StreetForm;
import kpn.financecontroller.gui.notifications.NotificationServiceImpl;
import kpn.financecontroller.gui.view.StreetView;
import org.springframework.stereotype.Component;

@Component
public final class StreetEventBindingBPP extends EventBindingBPP<Street> {
    @Override
    protected void doBinding() {
        form.addListener(StreetSaveButtonOnClickEvent.class, viewController::handleSavingEvent);
        viewController.addListener(StreetAfterSavingEventOnClickEvent.class, gridView::handleSavingEvent);
        form.addListener(StreetDeleteButtonOnClickEvent.class, viewController::handleDeletingEvent);
        viewController.addListener(StreetAfterDeletingEventOnClickEvent.class, gridView::handleDeletingEvent);
        form.addListener(StreetCancelButtonOnClickEvent.class, gridView::handleCancelEvent);
        viewController.addListener(StreetControllerNotificationEvent.class, notificationService::notify);
        gridView.addListener(StreetViewNotificationEvent.class, notificationService::notify);
    }

    @Override
    protected ChainLink createChainLink() {
        return new ChainLink(createNotificationServiceChainLink1(NotificationServiceImpl.class))
                .addNext(createViewControllerChainLink1(StreetViewController.class))
                .addNext(createFormChainLink1(StreetForm.class))
                .addNext(createGridViewChainLink1(StreetView.class));
    }
}
