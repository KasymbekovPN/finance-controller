package kpn.financecontroller.gui.binding.event;

import kpn.financecontroller.data.domain.Payment;
import kpn.financecontroller.gui.controller.PaymentViewController;
import kpn.financecontroller.gui.event.payment.controller.PaymentAfterDeletingEvent;
import kpn.financecontroller.gui.event.payment.controller.PaymentAfterSavingEvent;
import kpn.financecontroller.gui.event.payment.controller.PaymentControllerNotificationEvent;
import kpn.financecontroller.gui.event.payment.form.PaymentCancelButtonOnClickEvent;
import kpn.financecontroller.gui.event.payment.form.PaymentDeleteButtonOnClickEvent;
import kpn.financecontroller.gui.event.payment.form.PaymentSaveButtonOnClickEvent;
import kpn.financecontroller.gui.event.payment.view.PaymentViewNotificationEvent;
import kpn.financecontroller.gui.form.PaymentForm;
import kpn.financecontroller.gui.notifications.NotificationServiceImpl;
import kpn.financecontroller.gui.view.PaymentView;
import org.springframework.stereotype.Component;

@Component
public final class PaymentEventBindingBPP extends EventBindingBPP<Payment> {
    @Override
    protected void doBinding() {
        form.addListener(PaymentSaveButtonOnClickEvent.class, viewController::handleSavingEvent);
        viewController.addListener(PaymentAfterSavingEvent.class, gridView::handleSavingEvent);
        form.addListener(PaymentDeleteButtonOnClickEvent.class, viewController::handleDeletingEvent);
        viewController.addListener(PaymentAfterDeletingEvent.class, gridView::handleDeletingEvent);
        form.addListener(PaymentCancelButtonOnClickEvent.class, gridView::handleCancelEvent);
        viewController.addListener(PaymentControllerNotificationEvent.class, notificationService::notify);
        gridView.addListener(PaymentViewNotificationEvent.class, notificationService::notify);
    }

    @Override
    protected ChainLink createChainLink() {
        return new ChainLink(createNotificationServiceChainLink(NotificationServiceImpl.class))
                .addNext(createViewControllerChainLink(PaymentViewController.class))
                .addNext(createFormChainLink(PaymentForm.class))
                .addNext(createGridViewChainLink(PaymentView.class));
    }
}
