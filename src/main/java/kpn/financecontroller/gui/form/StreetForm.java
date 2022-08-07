package kpn.financecontroller.gui.form;

import com.querydsl.core.types.Predicate;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import kpn.financecontroller.data.domain.City;
import kpn.financecontroller.data.domain.Street;
import kpn.financecontroller.gui.event.street.form.StreetCancelButtonOnClickEvent;
import kpn.financecontroller.gui.event.street.form.StreetDeleteButtonOnClickEvent;
import kpn.financecontroller.gui.event.street.form.StreetSaveButtonOnClickEvent;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public final class StreetForm extends Form<Street> {
    private final TextField name = new TextField();
    private final ComboBox<City> city = new ComboBox<>();

    public StreetForm(Service<Long, City, Predicate, Result<List<City>>> cityStreet) {
        super(new Binder<>(Street.class));
        addClassName("street-form");
        binder.bindInstanceFields(this);

        name.setLabel(getTranslation("gui.label.name"));
        name.setPlaceholder(getTranslation("gui.placeholder.type-name"));

        city.setLabel(getTranslation("gui.label.city"));
        city.setItems(cityStreet.loader().all().getValue());
        city.setItemLabelGenerator(City::getInfo);

        add(
                name,
                city,
                createButtonsLayout()
        );

        setWidth("25em");
        close(true);
    }

    @Override
    protected ComponentEvent<?> createSaveButtonOnClickEvent() {
        return new StreetSaveButtonOnClickEvent(this, value);
    }

    @Override
    protected ComponentEvent<?> createCancelButtonOnClickEvent() {
        return new StreetCancelButtonOnClickEvent(this);
    }

    @Override
    protected ComponentEvent<?> createDeleteButtonOnClickEvent() {
        return new StreetDeleteButtonOnClickEvent(this, value);
    }
}
