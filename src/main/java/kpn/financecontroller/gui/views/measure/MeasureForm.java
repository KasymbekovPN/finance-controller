package kpn.financecontroller.gui.views.measure;

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
import kpn.financecontroller.data.domains.measure.Measure;
import kpn.financecontroller.gui.events.CloseFormEvent;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;

public class MeasureForm extends FormLayout {

    private final TextField code = new TextField("Code", "type code...");

    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");
    private final Button close = new Button("Cancel");

    private final Binder<Measure> binder = new Binder<>(Measure.class);

    private Measure measure;

    public MeasureForm() {
        addClassName("measure-form");
        binder.bindInstanceFields(this);

        add(
                code,
                createButtonsLayout()
        );
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
        binder.readBean(measure);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new MeasureDeleteFormEvent(this, measure)));
        close.addClickListener(event -> fireEvent(new MeasureCloseFormEvent(this)));

        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
      try{
           binder.writeBean(measure);
           fireEvent(new MeasureSaveFormEvent(this, measure));
      } catch (ValidationException ex){
           ex.printStackTrace();
      }
    }

    @Override
    protected <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
         return getEventBus().addListener(eventType, listener);
    }

    public static class MeasureSaveFormEvent extends SaveFormEvent<MeasureForm, Measure> {
        public MeasureSaveFormEvent(MeasureForm source, Measure value) {
            super(source, value);
        }
    }

    public static class MeasureDeleteFormEvent extends DeleteFormEvent<MeasureForm, Measure> {
        public MeasureDeleteFormEvent(MeasureForm source, Measure value) {
            super(source, value);
        }
    }

    public static class MeasureCloseFormEvent extends CloseFormEvent<MeasureForm, Measure> {
        public MeasureCloseFormEvent(MeasureForm source) {
            super(source);
        }
    }
}
