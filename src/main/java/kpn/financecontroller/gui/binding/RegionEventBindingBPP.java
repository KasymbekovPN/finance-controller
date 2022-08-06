package kpn.financecontroller.gui.binding;

import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.gui.controller.RegionViewController;
import kpn.financecontroller.gui.event.region.controller.RegionAfterDeletingEvent;
import kpn.financecontroller.gui.event.region.controller.RegionAfterSavingEvent;
import kpn.financecontroller.gui.event.region.controller.RegionControllerNotificationEvent;
import kpn.financecontroller.gui.event.region.form.RegionCancelButtonOnClickEvent;
import kpn.financecontroller.gui.event.region.form.RegionDeleteButtonOnClickEvent;
import kpn.financecontroller.gui.event.region.form.RegionSaveButtonOnClickEvent;
import kpn.financecontroller.gui.event.region.view.RegionViewNotificationEvent;
import kpn.financecontroller.gui.form.RegionForm;
import kpn.financecontroller.gui.notifications.NotificationServiceImpl;
import kpn.financecontroller.gui.view.RegionView;
import org.springframework.stereotype.Component;

@Component
public final class RegionEventBindingBPP extends EventBindingBPP<Region> {
    @Override
    protected void doBinding() {
        form.addListener(RegionSaveButtonOnClickEvent.class, viewController::handleSavingEvent);
        viewController.addListener(RegionAfterSavingEvent.class, gridView::handleSavingEvent);
        form.addListener(RegionDeleteButtonOnClickEvent.class, viewController::handleDeletingEvent);
        viewController.addListener(RegionAfterDeletingEvent.class, gridView::handleDeletingEvent);
        form.addListener(RegionCancelButtonOnClickEvent.class, gridView::handleCancelEvent);
        viewController.addListener(RegionControllerNotificationEvent.class, notificationService::notify);
        gridView.addListener(RegionViewNotificationEvent.class, notificationService::notify);
    }

    @Override
    protected ChainLink createChainLink() {
        return new ChainLink(createNotificationServiceChainLink1(NotificationServiceImpl.class))
                .addNext(createViewControllerChainLink1(RegionViewController.class))
                .addNext(createFormChainLink1(RegionForm.class))
                .addNext(createGridViewChainLink1(RegionView.class));
    }
}
