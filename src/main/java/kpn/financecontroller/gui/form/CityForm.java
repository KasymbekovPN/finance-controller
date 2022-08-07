package kpn.financecontroller.gui.form;

import com.querydsl.core.types.Predicate;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import kpn.financecontroller.data.domain.City;
import kpn.financecontroller.data.domain.Region;
import kpn.financecontroller.gui.event.city.form.CityCancelButtonOnClickEvent;
import kpn.financecontroller.gui.event.city.form.CityDeleteButtonOnClickEvent;
import kpn.financecontroller.gui.event.city.form.CitySaveButtonOnClickEvent;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public final class CityForm extends Form<City>{
    private final TextField name = new TextField();
    private final ComboBox<Region> region = new ComboBox<>();

    public CityForm(Service<Long, Region, Predicate, Result<List<Region>>> regionService) {
        super(new Binder<>(City.class));
        addClassName("city-form");
        binder.bindInstanceFields(this);

        name.setLabel(getTranslation("gui.label.name"));
        name.setPlaceholder(getTranslation("gui.placeholder.type-name"));

        region.setLabel(getTranslation("gui.label.region"));
        region.setItems(regionService.loader().all().getValue());
        region.setItemLabelGenerator(Region::getInfo);

        add(
                name,
                region,
                createButtonsLayout()
        );

        setWidth("25em");
        close(true);
    }

    @Override
    protected ComponentEvent<?> createSaveButtonOnClickEvent() {
        return new CitySaveButtonOnClickEvent(this, value);
    }

    @Override
    protected ComponentEvent<?> createCancelButtonOnClickEvent() {
        return new CityCancelButtonOnClickEvent(this);
    }

    @Override
    protected ComponentEvent<?> createDeleteButtonOnClickEvent() {
        return new CityDeleteButtonOnClickEvent(this, value);
    }
}
