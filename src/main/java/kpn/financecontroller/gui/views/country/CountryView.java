package kpn.financecontroller.gui.views.country;

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
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.gui.notifications.NotificationFactory;
import kpn.financecontroller.gui.notifications.Notifications;
import kpn.financecontroller.gui.views.MainLayout;
import kpn.financecontroller.i18n.I18nService;
import kpn.financecontroller.message.LocaledMessageSeed;
import kpn.financecontroller.message.LocaledMessageSeedFactory;
import kpn.financecontroller.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;

// TODO: 08.01.2022 make base class ???

@PageTitle("Country") // TODO: 08.01.2022 translation
@org.springframework.stereotype.Component
@Scope("prototype")
@Route(value = "country", layout = MainLayout.class)
@PermitAll
public class CountryView extends VerticalLayout {

    private final Grid<Country> grid = new Grid<>(Country.class);
    private final TextField filter = new TextField("", "filter by name..."); // TODO: 08.01.2022 translate it

    private final DTOService<Country, CountryEntity, Long> service;

    private final LocaledMessageSeedFactory seedFactory;
    private final I18nService i18nService;
    private final NotificationFactory notificationFactory;

    private CountryForm form;

    @Autowired
    public CountryView(DTOService<Country, CountryEntity, Long> service, LocaledMessageSeedFactory seedFactory, I18nService i18nService, NotificationFactory notificationFactory) {
        this.service = service;
        this.seedFactory = seedFactory;
        this.i18nService = i18nService;
        this.notificationFactory = notificationFactory;

        addClassName("country-view");
        setSizeFull();

        configureGrid();
        configureForm();

        add(getToolBar(), getContent());

        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassName("country-grid");
        grid.setSizeFull();
        grid.setColumns("id", "name");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editCountry(e.getValue()));
    }

    private void configureForm() {
        form = new CountryForm();
        form.setWidth("25em");

        form.addListener(CountryForm.CountrySaveFormEvent.class, this::saveContact);
        form.addListener(CountryForm.CountryDeleteFormEvent.class, this::deleteEvent);
        form.addListener(CountryForm.CountryCloseFormEvent.class, e -> closeEditor());
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
        form.setCountry(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void editCountry(Country country){
        if (country == null){
            closeEditor();
        } else {
            form.setCountry(country);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void deleteEvent(CountryForm.CountryDeleteFormEvent event) {
        service.deleter().byId(event.getValue().getId());

        updateList();
        closeEditor();
    }

    private void saveContact(CountryForm.CountrySaveFormEvent event) {
        Result<Country> savingResult = service.saver().save(new CountryEntity(event.getValue()));
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
        editCountry(new Country());
    }
}
