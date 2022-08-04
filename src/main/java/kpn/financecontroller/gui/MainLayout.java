package kpn.financecontroller.gui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.RouterLink;
import kpn.financecontroller.gui.generators.ClassAliasGenerator;
import kpn.financecontroller.gui.view.PaymentView;
import kpn.financecontroller.gui.view.ProductView;
import kpn.financecontroller.gui.view.SellerView;
import kpn.financecontroller.gui.view.TagView;
import kpn.financecontroller.gui.view.geo.address.AddressViewOld;
import kpn.financecontroller.gui.view.geo.city.CityViewOld;
import kpn.financecontroller.gui.view.geo.country.CountryViewOld;
import kpn.financecontroller.gui.view.geo.region.RegionViewOld;
import kpn.financecontroller.gui.view.geo.street.StreetViewOld;
import kpn.financecontroller.gui.view.statistic.ByTagStatistic;
import kpn.financecontroller.security.SecurityService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

final public class MainLayout extends AppLayout {

    private static final List<Class<? extends Component>> MENU_CLASSES = List.of(
//            PaymentViewOld.class,
            PaymentView.class,
//            ProductViewOld.class, // TODO: 04.08.2022 del
            ProductView.class,
//            TagViewOld.class, // TODO: 30.07.2022 ???
            TagView.class,
//            SellerViewOld.class, // TODO: 02.08.2022 del
            SellerView.class,
            AddressViewOld.class,
            StreetViewOld.class,
            CityViewOld.class,
            RegionViewOld.class,
            CountryViewOld.class,
            ByTagStatistic.class
    );

    @Setter
    @Getter
    @AllArgsConstructor
    public static class MenuItemInfo {
        private String text;
        private String iconClass;
        private Class<? extends Component> view;
    }

    private final H1 viewTitle = new H1();
    private final SecurityService securityService;
    private final ClassAliasGenerator classAliasGenerator;

    @Autowired
    public MainLayout(SecurityService securityService, ClassAliasGenerator classAliasGenerator) {
        this.securityService = securityService;
        this.classAliasGenerator = classAliasGenerator;
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        addToDrawer(createDrawerComponent());
    }

    private Component createHeaderContent() {
        DrawerToggle toggle = createAndCustomizeToggle();
        customizeViewTitle();
        return createAndCustomizeHeader(toggle, viewTitle);
    }

    private Component createAndCustomizeHeader(DrawerToggle toggle, H1 viewTitle) {

        Button logout = new Button(getTranslation("gui.login.logout"), e -> securityService.logout());

        HorizontalLayout header = new HorizontalLayout(toggle, viewTitle, logout);
        header.addClassNames("bg-base", "border-b", "border-contrast-10", "box-border", "flex", "h-xl", "items-center",
                "w-full");

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(viewTitle);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        return header;
    }

    private void customizeViewTitle() {
        viewTitle.addClassNames("m-0", "text-l");
    }

    private DrawerToggle createAndCustomizeToggle() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.addClassName("text-secondary");
        toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        toggle.getElement().setAttribute("aria-label", "Menu toggle");
        return toggle;
    }

    private Component createDrawerComponent() {
        H2 appName = createAndCustomizeAppName();
        return createAndCustomizeSection(appName);
    }

    private H2 createAndCustomizeAppName() {
        H2 appName = new H2("FC");
        appName.addClassNames("flex", "items-center", "h-xl", "m-0", "px-m", "text-m");
        return appName;
    }
    private com.vaadin.flow.component.html.Section createAndCustomizeSection(H2 appName) {
        com.vaadin.flow.component.html.Section section = new com.vaadin.flow.component.html.Section(appName,
                createNavigation(), createFooter());
        section.addClassNames("flex", "flex-col", "items-stretch", "max-h-full", "min-h-full");
        return section;
    }

    private Component createNavigation() {
        Nav nav = new Nav();
        nav.addClassNames("border-b", "border-contrast-10", "flex-grow", "overflow-auto");
        nav.getElement().setAttribute("aria-labelledby", "views");

        UnorderedList list = new UnorderedList();
        list.addClassNames("list-none", "m-0", "p-0");
        nav.add(list);

        for (RouterLink link : createLinks()) {
            ListItem item = new ListItem(link);
            list.add(item);
        }
        return nav;
    }

    private List<RouterLink> createLinks() {
        List<RouterLink> links = new ArrayList<>();
        for (Class<? extends Component> menuClass : MENU_CLASSES) {
            MenuItemInfo menuItemInfo = createMenuItemInfo(menuClass);
            links.add(createLink(menuItemInfo));
        }
        return links;
    }

    private MenuItemInfo createMenuItemInfo(Class<? extends Component> type) {
        String text = classAliasGenerator.generate(type);
        return new MenuItemInfo(text, "la la-globe", type);
    }

    private RouterLink createLink(MenuItemInfo menuItemInfo) {
        RouterLink link = new RouterLink();
        link.addClassNames("flex", "mx-s", "p-s", "relative", "text-secondary");
        link.setRoute(menuItemInfo.getView());

        Span icon = new Span();
        icon.addClassNames("me-s", "text-l");
        if (!menuItemInfo.getIconClass().isEmpty()) {
            icon.addClassNames(menuItemInfo.getIconClass());
        }

        Span text = new Span(getTranslation(menuItemInfo.getText()));
        text.addClassNames("font-medium", "text-s");

        link.add(icon, text);
        return link;
    }

    private Component createFooter() {
        Footer layout = new Footer();
        layout.addClassNames("flex", "items-center", "my-s", "px-m", "py-xs");

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        HasDynamicTitle content = (HasDynamicTitle) getContent();
        return content.getPageTitle();
    }
}
