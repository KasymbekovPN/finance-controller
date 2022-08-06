package kpn.financecontroller.gui.form;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.gui.event.country.form.CountryCancelButtonOnClickEvent;
import kpn.financecontroller.gui.event.country.form.CountryDeleteButtonOnClickEvent;
import kpn.financecontroller.gui.event.country.form.CountrySaveButtonOnClickEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public final class CountryForm extends Form<Country>{

    private final TextField name = new TextField();

    public CountryForm() {
        super(new Binder<>(Country.class));
        addClassName("country-form");
        binder.bindInstanceFields(this);

        name.setLabel(getTranslation("gui.label.name"));
        name.setPlaceholder(getTranslation("gui.placeholder.type-name"));

        add(
                name,
                createButtonsLayout()
        );

        setWidth("25em");
        close(true);
    }

    @Override
    protected ComponentEvent<?> createSaveButtonOnClickEvent() {
        return new CountrySaveButtonOnClickEvent(this, value);
    }

    @Override
    protected ComponentEvent<?> createCancelButtonOnClickEvent() {
        return new CountryCancelButtonOnClickEvent(this);
    }

    @Override
    protected ComponentEvent<?> createDeleteButtonOnClickEvent() {
        return new CountryDeleteButtonOnClickEvent(this, value);
    }
}
