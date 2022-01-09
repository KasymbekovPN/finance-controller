package kpn.financecontroller.gui.views.currency;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.currency.Currency;
import kpn.financecontroller.data.entities.currency.CurrencyEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.gui.notifications.NotificationFactory;
import kpn.financecontroller.gui.notifications.Notifications;
import kpn.financecontroller.gui.views.MainLayout;
import kpn.financecontroller.i18n.I18nService;
import kpn.financecontroller.message.LocaledMessageSeed;
import kpn.financecontroller.message.MessageSeedFactory;
import kpn.financecontroller.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;

@PageTitle("Currency")
@org.springframework.stereotype.Component
@Scope("prototype")
@Route(value = "currency", layout = MainLayout.class)
@PermitAll
public class CurrencyView extends VerticalLayout {

    private final Grid<Currency> grid = new Grid<>(Currency.class);
    private final TextField filter = new TextField("", "filter by code...");
    private final DTOService<Currency, CurrencyEntity, Long> service;
    private final MessageSeedFactory seedFactory;
    private final I18nService i18nService;
    private final NotificationFactory notificationFactory;

    private CurrencyForm form;

    @Autowired
    public CurrencyView(DTOService<Currency, CurrencyEntity, Long> service, MessageSeedFactory seedFactory, I18nService i18nService, NotificationFactory notificationFactory) {
        this.service = service;
        this.seedFactory = seedFactory;
        this.i18nService = i18nService;
        this.notificationFactory = notificationFactory;

        addClassName("currency-view");
        setSizeFull();

        configureGrid();
        configureForm();

        add(getToolBar(), getContent());

        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassName("currency-grid");
        grid.setSizeFull();
        grid.setColumns("id", "code");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editCurrency(e.getValue()));
    }

    private void configureForm() {
        form = new CurrencyForm();
        form.setWidth("25em");

        form.addListener(CurrencyForm.CurrencySaveFormEvent.class, this::saveContact);
        form.addListener(CurrencyForm.CurrencyDeleteFormEvent.class, this::deleteEvent);
        form.addListener(CurrencyForm.CurrencyCloseFormEvent.class, e -> closeEditor());
    }

    private Component getToolBar() {
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add");
        addContactButton.addClickListener(e -> addContact());

        HorizontalLayout toolbar = new HorizontalLayout(filter, addContactButton);
        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.addClassName("content");
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.setSizeFull();
        return content;
    }

    private void updateList() {
        grid.setItems(service.loader().all().getValue());
    }

    private void closeEditor() {
        form.setCurrency(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void editCurrency(Currency currency) {
        if (currency == null){
            closeEditor();
        } else {
            form.setCurrency(currency);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void deleteEvent(CurrencyForm.CurrencyDeleteFormEvent event) {
        service.deleter().byId(event.getValue().getId());

        updateList();
        closeEditor();
    }

    private void saveContact(CurrencyForm.CurrencySaveFormEvent event) {
        Result<Currency> savingResult = service.saver().save(new CurrencyEntity(event.getValue()));
        if (!savingResult.getSuccess()){
            LocaledMessageSeed seed = seedFactory.create(savingResult);
            String text = i18nService.getTranslation(seed);
            notificationFactory.getBuilder(Notifications.ERROR)
                    .duration(60_000)
                    .text(text)
                    .buttonIcon(new Icon("lumo", "cross"))
                    .buttonThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE)
                    .buttonAttribute("aria-label", "Close")
                    .build()
                    .open();
        }
        updateList();
        closeEditor();
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editCurrency(new Currency());
    }
}
