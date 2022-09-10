package kpn.financecontroller.gui.binding.event;

import kpn.financecontroller.data.domain.Product;
import kpn.financecontroller.gui.controller.ProductViewController;
import kpn.financecontroller.gui.event.product.controller.ProductAfterDeletingEvent;
import kpn.financecontroller.gui.event.product.controller.ProductAfterSavingEvent;
import kpn.financecontroller.gui.event.product.controller.ProductControllerNotificationEvent;
import kpn.financecontroller.gui.event.product.form.ProductCancelButtonOnClickEvent;
import kpn.financecontroller.gui.event.product.form.ProductDeleteButtonOnClickEvent;
import kpn.financecontroller.gui.event.product.form.ProductSaveButtonOnClickEvent;
import kpn.financecontroller.gui.event.product.view.ProductViewNotificationEvent;
import kpn.financecontroller.gui.form.ProductForm;
import kpn.financecontroller.gui.notifications.NotificationServiceImpl;
import kpn.financecontroller.gui.view.ProductView;
import org.springframework.stereotype.Component;

@Component
public final class ProductEventBindingBPP extends EventBindingBPP<Product> {
    @Override
    protected void doBinding() {
        form.addListener(ProductSaveButtonOnClickEvent.class, viewController::handleSavingEvent);
        viewController.addListener(ProductAfterSavingEvent.class, gridView::handleSavingEvent);
        form.addListener(ProductDeleteButtonOnClickEvent.class, viewController::handleDeletingEvent);
        viewController.addListener(ProductAfterDeletingEvent.class, gridView::handleDeletingEvent);
        form.addListener(ProductCancelButtonOnClickEvent.class, gridView::handleCancelEvent);
        viewController.addListener(ProductControllerNotificationEvent.class, notificationService::notify);
        gridView.addListener(ProductViewNotificationEvent.class, notificationService::notify);
    }

    @Override
    protected ChainLink createChainLink() {
        return new ChainLink(createNotificationServiceChainLink(NotificationServiceImpl.class))
                .addNext(createViewControllerChainLink(ProductViewController.class))
                .addNext(createFormChainLink(ProductForm.class))
                .addNext(createGridViewChainLink(ProductView.class));
    }
}
