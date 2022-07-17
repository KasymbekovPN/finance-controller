// TODO: 17.07.2022 restore
//package kpn.financecontroller.gui.views.geo.region;
//
//import com.vaadin.flow.component.combobox.ComboBox;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.data.binder.Binder;
//import kpn.financecontroller.data.domains.country.Country;
//import kpn.financecontroller.data.domains.region.Region;
//import kpn.financecontroller.gui.events.CloseFormEvent;
//import kpn.financecontroller.gui.events.DeleteFormEvent;
//import kpn.financecontroller.gui.events.SaveFormEvent;
//import kpn.financecontroller.gui.views.EditForm;
//
//import java.util.List;
//
//final public class RegionForm extends EditForm<Region> {
//
//    private final TextField name = new TextField();
//    private final ComboBox<Country> country = new ComboBox<>();
//
//    public RegionForm(List<Country> countries) {
//        super(new Binder<>(Region.class));
//        addClassName("region-form");
//        binder.bindInstanceFields(this);
//
//        name.setLabel(getTranslation("gui.label.name"));
//        name.setPlaceholder(getTranslation("gui.placeholder.type-name"));
//        country.setLabel(getTranslation("gui.label.country"));
//
//        country.setItems(countries);
//        country.setItemLabelGenerator(Country::getName);
//
//        add(
//                name,
//                country,
//                createButtonsLayout()
//        );
//    }
//
//    @Override
//    protected SaveFormEvent<EditForm<Region>, Region> createSaveEvent() {
//        return new RegionSaveFormEvent(this, value);
//    }
//
//    @Override
//    protected DeleteFormEvent<EditForm<Region>, Region> createDeleteEvent() {
//        return new RegionDeleteFormEvent(this, value);
//    }
//
//    @Override
//    protected CloseFormEvent<EditForm<Region>, Region> createCloseEvent() {
//        return new RegionCloseFormEvent(this);
//    }
//
//    public static class RegionSaveFormEvent extends SaveFormEvent<EditForm<Region>, Region> {
//        public RegionSaveFormEvent(EditForm<Region> source, Region value) {
//            super(source, value);
//        }
//    }
//
//    public static class RegionDeleteFormEvent extends DeleteFormEvent<EditForm<Region>, Region> {
//        public RegionDeleteFormEvent(EditForm<Region> source, Region value) {
//            super(source, value);
//        }
//    }
//
//    public static class RegionCloseFormEvent extends CloseFormEvent<EditForm<Region>, Region> {
//        public RegionCloseFormEvent(EditForm<Region> source) {
//            super(source);
//        }
//    }
//}
