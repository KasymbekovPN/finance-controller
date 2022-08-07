package kpn.financecontroller.gui.binding;

import kpn.financecontroller.data.domain.City;
import kpn.financecontroller.gui.controller.CityViewController;
import kpn.financecontroller.gui.event.city.controller.CityAfterDeletingEvent;
import kpn.financecontroller.gui.event.city.controller.CityAfterSavingEvent;
import kpn.financecontroller.gui.event.city.controller.CityControllerNotificationEvent;
import kpn.financecontroller.gui.event.city.form.CityCancelButtonOnClickEvent;
import kpn.financecontroller.gui.event.city.form.CityDeleteButtonOnClickEvent;
import kpn.financecontroller.gui.event.city.form.CitySaveButtonOnClickEvent;
import kpn.financecontroller.gui.event.city.view.CityViewNotificationEvent;
import kpn.financecontroller.gui.form.CityForm;
import kpn.financecontroller.gui.notifications.NotificationServiceImpl;
import kpn.financecontroller.gui.view.CityView;
import org.springframework.stereotype.Component;

@Component
public final class CityEventBindingBPP extends EventBindingBPP<City> {
    @Override
    protected void doBinding() {
        form.addListener(CitySaveButtonOnClickEvent.class, viewController::handleSavingEvent);
        viewController.addListener(CityAfterSavingEvent.class, gridView::handleSavingEvent);
        form.addListener(CityDeleteButtonOnClickEvent.class, viewController::handleDeletingEvent);
        viewController.addListener(CityAfterDeletingEvent.class, gridView::handleDeletingEvent);
        form.addListener(CityCancelButtonOnClickEvent.class, gridView::handleCancelEvent);
        viewController.addListener(CityControllerNotificationEvent.class, notificationService::notify);
        gridView.addListener(CityViewNotificationEvent.class, notificationService::notify);
    }

    @Override
    protected ChainLink createChainLink() {
        return new ChainLink(createNotificationServiceChainLink1(NotificationServiceImpl.class))
                .addNext(createViewControllerChainLink1(CityViewController.class))
                .addNext(createFormChainLink1(CityForm.class))
                .addNext(createGridViewChainLink1(CityView.class));
    }
}
