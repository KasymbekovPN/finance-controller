package kpn.financecontroller.gui.binding;

import kpn.financecontroller.data.domains.payment.Payment;
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

import java.util.function.Function;

@Component
public final class PaymentEventBindingBPP extends EventBindingBPP<Payment> {
    @Override
    protected boolean checkBindingCondition() {
        return form != null && gridView != null && viewController != null;
    }

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
            if (bean.getClass().equals(PaymentViewController.class)){
                viewController = (PaymentViewController) bean;
                return true;
            }
            return false;
        };
    }

    private Function<Object, Boolean> createFormChainLink() {
        return  (Object bean) -> {
            if (bean.getClass().equals(PaymentForm.class)){
                form = (PaymentForm) bean;
                return true;
            }
            return false;
        };
    }

    private Function<Object, Boolean> createGridViewChainLink() {
        return  (Object bean) -> {
            if (bean.getClass().equals(PaymentView.class)){
                gridView = (PaymentView) bean;
                return true;
            }
            return false;
        };
    }
}
