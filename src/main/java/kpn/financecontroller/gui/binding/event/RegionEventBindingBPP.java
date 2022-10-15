package kpn.financecontroller.gui.binding.event;

import kpn.financecontroller.data.domain.Region;
import kpn.financecontroller.gui.controller.RegionViewController;
import kpn.financecontroller.gui.event.region.controller.RegionAfterDeletingEvent;
import kpn.financecontroller.gui.event.region.controller.RegionAfterSavingEvent;
import kpn.financecontroller.gui.event.region.controller.RegionControllerNotificationEvent;
import kpn.financecontroller.gui.event.region.form.RegionCancelButtonOnClickEvent;
import kpn.financecontroller.gui.event.region.form.RegionDeleteButtonOnClickEvent;
import kpn.financecontroller.gui.event.region.form.RegionSaveButtonOnClickEvent;
import kpn.financecontroller.gui.event.region.view.RegionViewNotificationEvent;
import kpn.financecontroller.gui.form.RegionForm;
import kpn.financecontroller.gui.view.RegionView;
import org.springframework.stereotype.Component;

@Component
public final class RegionEventBindingBPP extends DomainEventBindingBPP<Region> {
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
        return super.createChainLink()
                .addNext(createViewControllerChainLink(RegionViewController.class))
                .addNext(createFormChainLink(RegionForm.class))
                .addNext(createGridViewChainLink(RegionView.class));
    }
}
