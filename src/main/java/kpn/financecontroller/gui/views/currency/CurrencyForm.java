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
import lombok.Getter;

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
          delete.addClickListener(event -> fireEvent(new DeleteEvent(this, currency)));
          close.addClickListener(event -> fireEvent(new CloseEvent(this)));

          binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

          return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
      try{
           binder.writeBean(currency);
           fireEvent(new SaveEvent(this, currency));
      } catch (ValidationException ex){
           ex.printStackTrace();
      }
    }

    // TODO: 05.01.2022 remake it as generic class
     @Getter
     public static abstract class ContactFormEvent extends ComponentEvent<CurrencyForm>{
          private final Currency currency;

          public ContactFormEvent(CurrencyForm source, Currency currency) {
               super(source, false);
               this.currency = currency;
          }
     }

     public static class SaveEvent extends ContactFormEvent{
          public SaveEvent(CurrencyForm source, Currency contact) {
               super(source, contact);
          }
     }

     public static class DeleteEvent extends ContactFormEvent{
          public DeleteEvent(CurrencyForm source, Currency contact) {
               super(source, contact);
          }
     }

     public static class CloseEvent extends ContactFormEvent{
          public CloseEvent(CurrencyForm source) {
               super(source, null);
          }
     }

     @Override
     protected <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
          return getEventBus().addListener(eventType, listener);
     }
}
