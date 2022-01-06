package kpn.financecontroller.gui.views.currency;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import kpn.financecontroller.data.domain.currency.Currency;
import kpn.financecontroller.gui.events.CloseFormEvent;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;

public class CurrencyForm extends FormLayout {
    private final TextField code = new TextField("Code", "type code...");

    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");
    private final Button close = new Button("Cancel");

    private final Binder<Currency> binder = new Binder<>(Currency.class);

    private Currency currency;

    public CurrencyForm() {
        addClassName("currency-form");
        binder.bindInstanceFields(this);

        add(
                code,
                createButtonsLayout()
        );
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
        binder.readBean(currency);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());

        delete.addClickListener(event -> fireEvent(new CurrencyDeleteFormEvent(this, currency)));
        close.addClickListener(event -> fireEvent(new CurrencyCloseFormEvent(this)));

        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
      try{
           binder.writeBean(currency);
           fireEvent(new CurrencySaveFormEvent(this, currency));
      } catch (ValidationException ex){
           ex.printStackTrace();
      }
    }

    @Override
    protected <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
         return getEventBus().addListener(eventType, listener);
    }

    public static class CurrencySaveFormEvent extends SaveFormEvent<CurrencyForm, Currency>{
        public CurrencySaveFormEvent(CurrencyForm source, Currency value) {
            super(source, value);
        }
    }

    public static class CurrencyDeleteFormEvent extends DeleteFormEvent<CurrencyForm, Currency>{
        public CurrencyDeleteFormEvent(CurrencyForm source, Currency value) {
            super(source, value);
        }
    }

    public static class CurrencyCloseFormEvent extends CloseFormEvent<CurrencyForm, Currency>{
        public CurrencyCloseFormEvent(CurrencyForm source) {
            super(source);
        }
    }
}
