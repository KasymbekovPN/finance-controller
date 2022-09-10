package kpn.financecontroller.gui.binding.event;

import kpn.financecontroller.data.domain.Address;
import kpn.financecontroller.gui.controller.AddressViewController;
import kpn.financecontroller.gui.event.address.controller.AddressAfterDeletingEvent;
import kpn.financecontroller.gui.event.address.controller.AddressAfterSavingEvent;
import kpn.financecontroller.gui.event.address.controller.AddressControllerNotificationEvent;
import kpn.financecontroller.gui.event.address.form.AddressCancelButtonOnClickEvent;
import kpn.financecontroller.gui.event.address.form.AddressDeleteButtonOnClickEvent;
import kpn.financecontroller.gui.event.address.form.AddressSaveButtonOnClickEvent;
import kpn.financecontroller.gui.event.address.view.AddressViewNotificationEvent;
import kpn.financecontroller.gui.form.AddressForm;
import kpn.financecontroller.gui.notifications.NotificationServiceImpl;
import kpn.financecontroller.gui.view.AddressView;
import org.springframework.stereotype.Component;

@Component
public final class AddressEventBindingBPP extends EventBindingBPP<Address> {
    @Override
    protected void doBinding() {
        form.addListener(AddressSaveButtonOnClickEvent.class, viewController::handleSavingEvent);
        viewController.addListener(AddressAfterSavingEvent.class, gridView::handleSavingEvent);
        form.addListener(AddressDeleteButtonOnClickEvent.class, viewController::handleDeletingEvent);
        viewController.addListener(AddressAfterDeletingEvent.class, gridView::handleDeletingEvent);
        form.addListener(AddressCancelButtonOnClickEvent.class, gridView::handleCancelEvent);
        viewController.addListener(AddressControllerNotificationEvent.class, notificationService::notify);
        gridView.addListener(AddressViewNotificationEvent.class, notificationService::notify);
    }

    @Override
    protected ChainLink createChainLink() {
        return new ChainLink(createNotificationServiceChainLink(NotificationServiceImpl.class))
                .addNext(createViewControllerChainLink(AddressViewController.class))
                .addNext(createFormChainLink(AddressForm.class))
                .addNext(createGridViewChainLink(AddressView.class));
    }
}
