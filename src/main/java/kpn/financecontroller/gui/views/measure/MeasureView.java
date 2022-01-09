package kpn.financecontroller.gui.views.measure;

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
import kpn.financecontroller.data.domains.measure.Measure;
import kpn.financecontroller.data.entities.measure.MeasureEntity;
import kpn.financecontroller.data.services.measure.MeasureService;
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

@PageTitle("Measure")
@org.springframework.stereotype.Component
@Scope("prototype")
@Route(value = "measure", layout = MainLayout.class)
@PermitAll
public class MeasureView extends VerticalLayout {

    private final Grid<Measure> grid = new Grid<>(Measure.class);
    private final TextField filter = new TextField("", "filter by code...");
    private final MeasureService service;
    private final MessageSeedFactory seedFactory;
    private final I18nService i18nService;
    private final NotificationFactory notificationFactory;

    private MeasureForm form;

    @Autowired
    public MeasureView(MeasureService service, MessageSeedFactory seedFactory, I18nService i18nService, NotificationFactory notificationFactory) {
        this.service = service;
        this.seedFactory = seedFactory;
        this.i18nService = i18nService;
        this.notificationFactory = notificationFactory;

        addClassName("measure-view");
        setSizeFull();

        configureGrid();
        configureForm();

        add(getToolBar(), getContent());

        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassName("measure-grid");
        grid.setSizeFull();
        grid.setColumns("id", "code");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editMeasure(e.getValue()));
    }

    private void editMeasure(Measure measure) {
        if (measure == null){
            closeEditor();
        } else {
            form.setMeasure(measure);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void configureForm() {
        form = new MeasureForm();
        form.setWidth("25em");

        form.addListener(MeasureForm.MeasureSaveFormEvent.class, this::saveContact);
        form.addListener(MeasureForm.MeasureDeleteFormEvent.class, this::deleteEvent);
        form.addListener(MeasureForm.MeasureCloseFormEvent.class, e -> closeEditor());
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
        grid.setItems(service.search(filter.getValue()).getValue());
    }

    private void closeEditor() {
        form.setMeasure(null);
        form.setVisible(false);
        removeClassName("editing");
    }


    private void deleteEvent(MeasureForm.MeasureDeleteFormEvent event) {
        service.deleteById(event.getValue().getId());
        updateList();
        closeEditor();
    }

    private void saveContact(MeasureForm.MeasureSaveFormEvent event) {
        Result<Measure> savingResult = service.save(new MeasureEntity(event.getValue()));
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
        editMeasure(new Measure());
    }
}
