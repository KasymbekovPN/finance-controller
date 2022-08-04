package kpn.financecontroller.gui.binding;

import kpn.financecontroller.data.domains.seller.Seller;
import kpn.financecontroller.gui.controller.SellerViewController;
import kpn.financecontroller.gui.event.seller.controller.SellerAfterDeletingEvent;
import kpn.financecontroller.gui.event.seller.controller.SellerAfterSavingEvent;
import kpn.financecontroller.gui.event.seller.controller.SellerControllerNotificationEvent;
import kpn.financecontroller.gui.event.seller.form.SellerCancelButtonOnClickEvent;
import kpn.financecontroller.gui.event.seller.form.SellerDeleteButtonOnClickEvent;
import kpn.financecontroller.gui.event.seller.form.SellerSaveButtonOnClickEvent;
import kpn.financecontroller.gui.event.seller.view.SellerViewNotificationEvent;
import kpn.financecontroller.gui.form.SellerForm;
import kpn.financecontroller.gui.notifications.NotificationServiceImpl;
import kpn.financecontroller.gui.view.SellerView;
import org.springframework.stereotype.Component;

@Component
public final class SellerEventBindingBPP extends EventBindingBPP<Seller> {
    @Override
    protected void doBinding() {
        form.addListener(SellerSaveButtonOnClickEvent.class, viewController::handleSavingEvent);
        viewController.addListener(SellerAfterSavingEvent.class, gridView::handleSavingEvent);
        form.addListener(SellerDeleteButtonOnClickEvent.class, viewController::handleDeletingEvent);
        viewController.addListener(SellerAfterDeletingEvent.class, gridView::handleDeletingEvent);
        form.addListener(SellerCancelButtonOnClickEvent.class, gridView::handleCancelEvent);
        viewController.addListener(SellerControllerNotificationEvent.class, notificationService::notify);
        gridView.addListener(SellerViewNotificationEvent.class, notificationService::notify);
    }

    @Override
    protected ChainLink createChainLink() {
        return new ChainLink(createNotificationServiceChainLink1(NotificationServiceImpl.class))
                .addNext(createViewControllerChainLink1(SellerViewController.class))
                .addNext(createFormChainLink1(SellerForm.class))
                .addNext(createGridViewChainLink1(SellerView.class));
    }
}
