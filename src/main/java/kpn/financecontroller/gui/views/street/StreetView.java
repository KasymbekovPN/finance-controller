package kpn.financecontroller.gui.views.street;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.entities.street.StreetEntity;
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

@PageTitle("Street")
@org.springframework.stereotype.Component
@Scope("prototype")
@Route(value = "street", layout = MainLayout.class)
@PermitAll
public class StreetView extends VerticalLayout {

    private final Grid<Street> grid = new Grid<>(Street.class);
    private final DTOService<Street, StreetEntity, Long> streetService;
    private final DTOService<City, CityEntity, Long> cityService;
    private final LocaledMessageSeedFactory seedFactory;
    private final I18nService i18nService;
    private final NotificationFactory notificationFactory;

    private StreetForm form;

    @Autowired
    public StreetView(DTOService<Street, StreetEntity, Long> streetService,
                      DTOService<City, CityEntity, Long> cityService,
                      LocaledMessageSeedFactory seedFactory,
                      I18nService i18nService,
                      NotificationFactory notificationFactory) {
        this.streetService = streetService;
        this.cityService = cityService;
        this.seedFactory = seedFactory;
        this.i18nService = i18nService;
        this.notificationFactory = notificationFactory;


        addClassName("street-view");
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
        grid.addColumn(street -> street.getCity().getName()).setHeader("City");
        grid.addColumn(street -> street.getCity().getRegion().getName()).setHeader("Region");
        grid.addColumn(street -> street.getCity().getRegion().getCountry().getName()).setHeader("Country");

        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editStreet(e.getValue()));
    }

    private void configureForm() {
        form = new StreetForm(cityService.loader().all().getValue());
        form.setWidth("25em");

        form.addListener(StreetForm.StreetSaveFormEvent.class, this::saveContact);
        form.addListener(StreetForm.StreetDeleteFormEvent.class, this::deleteEvent);
        form.addListener(StreetForm.StreetCloseFormEvent.class, e -> closeEditor());
    }

    private Component getToolBar() {

        Button addContactButton = new Button("Add");
        addContactButton.addClickListener(e -> addContact());

        HorizontalLayout toolbar = new HorizontalLayout(addContactButton);
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
        grid.setItems(streetService.loader().all().getValue());
    }

    private void closeEditor() {
        form.setStreet(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void editStreet(Street street){
        if (street == null){
            closeEditor();
        } else {
            form.setStreet(street);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void deleteEvent(StreetForm.StreetDeleteFormEvent event) {
        cityService.deleter().byId(event.getValue().getId());

        updateList();
        closeEditor();
    }

    private void saveContact(StreetForm.StreetSaveFormEvent event) {
        Result<Street> savingResult = streetService.saver().save(new StreetEntity(event.getValue()));
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
        editStreet(new Street());
    }
}
