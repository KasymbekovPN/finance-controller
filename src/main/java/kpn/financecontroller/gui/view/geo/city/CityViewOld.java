// TODO: 06.08.2022 del
//package kpn.financecontroller.gui.view.geo.city;
//
//import com.querydsl.core.types.Predicate;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.router.Route;
//import kpn.financecontroller.data.domains.city.City;
//import kpn.financecontroller.data.domains.region.Region;
//import kpn.financecontroller.gui.view.GridViewOld;
//import kpn.financecontroller.gui.MainLayout;
//import kpn.lib.result.Result;
//import kpn.lib.service.Service;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Scope;
//
//import javax.annotation.security.PermitAll;
//import java.util.List;
//
//@Scope("prototype")
//@Route(value = "city", layout = MainLayout.class)
//@PermitAll
//final public class CityViewOld extends GridViewOld<City> {
//    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
//            new ColumnConfig("gui.header.id", List.of("id")),
//            new ColumnConfig("gui.header.name", List.of("name")),
//            new ColumnConfig("gui.header.region", List.of("region", "name")),
//            new ColumnConfig("gui.header.country", List.of("region", "country", "name"))
//    );
//
//    @Autowired
//    private Service<Long, City, Predicate, Result<List<City>>> cityService;
//    @Autowired
//    private Service<Long, Region, Predicate, Result<List<Region>>> regionService;
//
//    @Override
//    protected Result<?> updateListImpl() {
//        Result<List<City>> result = cityService.loader().all();
//        if (result.isSuccess()){
//            grid.setItems(result.getValue());
//        }
//        return result;
//    }
//
//    @Override
//    protected void configureGrid() {
//        grid = new Grid<>(City.class);
//        grid.addClassName("country-grid");
//        grid.setSizeFull();
//        configureGridColumns(COLUMN_CONFIGS);
//        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
//    }
//
//    @Override
//    protected void configureForm() {
//        form = new CityFormOld(regionService.loader().all().getValue());
//        form.setWidth("25em");
//
//        form.addListener(CityFormOld.CitySaveFormEvent.class, this::handleSavingEvent);
//        form.addListener(CityFormOld.CityDeleteFormEvent.class, this::handleDeletingEvent);
//        form.addListener(CityFormOld.CityCloseFormEvent.class, e -> closeEditor(true));
//    }
//
//    @Override
//    protected Result<List<City>> delete(City domain) {
//        return cityService.deleter().byId(domain.getId());
//    }
//
//    @Override
//    protected Result<List<City>> save(City domain) {
//        return cityService.saver().save(domain);
//    }
//}
