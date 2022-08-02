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

import java.util.function.Function;

@Component
public final class SellerEventBindingBPP extends EventBindingBPP<Seller> {
    @Override
    protected boolean checkBindingCondition() {
        return form != null && gridView != null && viewController != null;
    }

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
            if (bean.getClass().equals(SellerViewController.class)){
                viewController = (SellerViewController) bean;
                return true;
            }
            return false;
        };
    }

    private Function<Object, Boolean> createFormChainLink() {
        return  (Object bean) -> {
            if (bean.getClass().equals(SellerForm.class)){
                form = (SellerForm) bean;
                return true;
            }
            return false;
        };
    }

    private Function<Object, Boolean> createGridViewChainLink() {
        return  (Object bean) -> {
            if (bean.getClass().equals(SellerView.class)){
                gridView = (SellerView) bean;
                return true;
            }
            return false;
        };
    }
}
