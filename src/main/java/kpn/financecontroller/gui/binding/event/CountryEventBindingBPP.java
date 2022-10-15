package kpn.financecontroller.gui.binding.event;

import kpn.financecontroller.data.domain.Country;
import kpn.financecontroller.gui.controller.CountryViewController;
import kpn.financecontroller.gui.event.country.controller.CountryAfterDeletingEvent;
import kpn.financecontroller.gui.event.country.controller.CountryAfterSavingEvent;
import kpn.financecontroller.gui.event.country.controller.CountryControllerNotificationEvent;
import kpn.financecontroller.gui.event.country.form.CountryCancelButtonOnClickEvent;
import kpn.financecontroller.gui.event.country.form.CountryDeleteButtonOnClickEvent;
import kpn.financecontroller.gui.event.country.form.CountrySaveButtonOnClickEvent;
import kpn.financecontroller.gui.event.country.view.CountryViewNotificationEvent;
import kpn.financecontroller.gui.form.CountryForm;
import kpn.financecontroller.gui.view.CountryView;
import org.springframework.stereotype.Component;

@Component
public final class CountryEventBindingBPP extends DomainEventBindingBPP<Country> {
    @Override
    protected void doBinding() {
        form.addListener(CountrySaveButtonOnClickEvent.class, viewController::handleSavingEvent);
        viewController.addListener(CountryAfterSavingEvent.class, gridView::handleSavingEvent);
        form.addListener(CountryDeleteButtonOnClickEvent.class, viewController::handleDeletingEvent);
        viewController.addListener(CountryAfterDeletingEvent.class, gridView::handleDeletingEvent);
        form.addListener(CountryCancelButtonOnClickEvent.class, gridView::handleCancelEvent);
        viewController.addListener(CountryControllerNotificationEvent.class, notificationService::notify);
        gridView.addListener(CountryViewNotificationEvent.class, notificationService::notify);
    }

    @Override
    protected ChainLink createChainLink() {
        return super.createChainLink()
                .addNext(createViewControllerChainLink(CountryViewController.class))
                .addNext(createFormChainLink(CountryForm.class))
                .addNext(createGridViewChainLink(CountryView.class));
    }
}
