package kpn.financecontroller.gui.binding;

import kpn.financecontroller.data.domains.product.Product;
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

import java.util.function.Function;

@Component
public final class ProductEventBindingBPP extends EventBindingBPP<Product> {
    @Override
    protected boolean checkBindingCondition() {
        return form != null && gridView != null && viewController != null;
    }

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
            if (bean.getClass().equals(ProductViewController.class)){
                viewController = (ProductViewController) bean;
                return true;
            }
            return false;
        };
    }

    private Function<Object, Boolean> createFormChainLink() {
        return  (Object bean) -> {
            if (bean.getClass().equals(ProductForm.class)){
                form = (ProductForm) bean;
                return true;
            }
            return false;
        };
    }

    private Function<Object, Boolean> createGridViewChainLink() {
        return  (Object bean) -> {
            if (bean.getClass().equals(ProductView.class)){
                gridView = (ProductView) bean;
                return true;
            }
            return false;
        };
    }
}
