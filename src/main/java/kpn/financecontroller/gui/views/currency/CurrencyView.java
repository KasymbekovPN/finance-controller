package kpn.financecontroller.gui.views.currency;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domain.currency.Currency;
import kpn.financecontroller.data.entities.currency.CurrencyEntity;
import kpn.financecontroller.data.service.currency.CurrencyService;
import kpn.financecontroller.gui.views.MainLayout;
import kpn.financecontroller.result.Result;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Currency")
@org.springframework.stereotype.Component
@Scope("prototype")
@Route(value = "currency", layout = MainLayout.class)
@PermitAll
public class CurrencyView extends VerticalLayout {

    private final Grid<Currency> grid = new Grid<>(Currency.class);
    private final TextField filter = new TextField("", "filter by code...");
    private final CurrencyService service;

    private CurrencyForm form;

    public CurrencyView(CurrencyService service) {
        this.service = service;

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
        grid.setItems(service.search(filter.getValue()).getValue());
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
        service.deleteById(event.getValue().getId());
        updateList();
        closeEditor();
    }

    private void saveContact(CurrencyForm.CurrencySaveFormEvent event) {
        Result<Currency> savingResult = service.save(new CurrencyEntity(event.getValue()));
        if (!savingResult.getSuccess()){
            String text = extractResultMessage(savingResult.getCode(), List.of(savingResult.getArgs()));
            createClosableErrorNotification(text, 60_000).open();
        }
        updateList();
        closeEditor();
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editCurrency(new Currency());
    }

    // TODO: 06.01.2022 it must be bean
    private String extractResultMessage(String code, List<Object> args){
        return code + " " + args;
    }

    // TODO: 06.01.2022 it must be bean
    private Notification createClosableErrorNotification(String text, int duration){
        Notification notification = new Notification("", duration);

        Div textDiv = new Div(new Text(String.format("[ERROR] %s", text)));

        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.addClickListener(event -> notification.close());

        HorizontalLayout layout = new HorizontalLayout(textDiv, closeButton);
        layout.setAlignItems(Alignment.CENTER);

        notification.add(layout);

        return notification;
    }
}
