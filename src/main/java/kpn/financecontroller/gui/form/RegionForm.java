package kpn.financecontroller.gui.form;

import com.querydsl.core.types.Predicate;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import kpn.financecontroller.data.domain.Country;
import kpn.financecontroller.data.domain.Region;
import kpn.financecontroller.gui.event.region.form.RegionCancelButtonOnClickEvent;
import kpn.financecontroller.gui.event.region.form.RegionDeleteButtonOnClickEvent;
import kpn.financecontroller.gui.event.region.form.RegionSaveButtonOnClickEvent;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public final class RegionForm extends Form<Region>{
    private final TextField name = new TextField();
    private final ComboBox<Country> country = new ComboBox<>();

    public RegionForm(Service<Long, Country, Predicate, Result<List<Country>>> countryService) {
        super(new Binder<>(Region.class));
        addClassName("region-form");
        binder.bindInstanceFields(this);

        name.setLabel(getTranslation("gui.label.name"));
        name.setPlaceholder(getTranslation("gui.placeholder.type-name"));
        country.setLabel(getTranslation("gui.label.country"));

        country.setItems(countryService.loader().all().getValue());
        country.setItemLabelGenerator(Country::getName);

        add(
                name,
                country,
                createButtonsLayout()
        );

        setWidth("25em");
        close(true);
    }

    @Override
    protected ComponentEvent<?> createSaveButtonOnClickEvent() {
        return new RegionSaveButtonOnClickEvent(this, value);
    }

    @Override
    protected ComponentEvent<?> createCancelButtonOnClickEvent() {
        return new RegionCancelButtonOnClickEvent(this);
    }

    @Override
    protected ComponentEvent<?> createDeleteButtonOnClickEvent() {
        return new RegionDeleteButtonOnClickEvent(this, value);
    }
}
