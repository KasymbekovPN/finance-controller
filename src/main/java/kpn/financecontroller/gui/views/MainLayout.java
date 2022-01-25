package kpn.financecontroller.gui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import kpn.financecontroller.gui.views.building.BuildingView;
import kpn.financecontroller.gui.views.city.CityView;
import kpn.financecontroller.gui.views.country.CountryView;
import kpn.financecontroller.gui.views.payment.PaymentView;
import kpn.financecontroller.gui.views.product.ProductView;
import kpn.financecontroller.gui.views.region.RegionView;
import kpn.financecontroller.gui.views.street.StreetView;
import kpn.financecontroller.gui.views.tag.TagView;
import kpn.financecontroller.security.SecurityService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class MainLayout extends AppLayout {

    private static final MenuItemInfo[] MENU_ITEMS = new MenuItemInfo[]{
            new MenuItemInfo("gui.payments",  "la la-globe", PaymentView.class),
            new MenuItemInfo("gui.products",  "la la-globe", ProductView.class),
            new MenuItemInfo("gui.tags",  "la la-globe", TagView.class),
            new MenuItemInfo("gui.buildings",  "la la-globe", BuildingView.class),
            new MenuItemInfo("gui.streets",  "la la-globe", StreetView.class),
            new MenuItemInfo("gui.cities",  "la la-globe", CityView.class),
            new MenuItemInfo("gui.regions", "la la-globe", RegionView.class),
            new MenuItemInfo("gui.countries", "la la-globe", CountryView.class)
    };

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

    @Autowired
    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
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

        Button logout = new Button("Log out", e -> securityService.logout());

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
        for (MenuItemInfo menuItemInfo : MENU_ITEMS) {
            links.add(createLink(menuItemInfo));

        }
        return links;
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
        Class<? extends Component> contentClass = getContent().getClass();
        if (contentClass.isAnnotationPresent(PageTitle.class)){
            String key = contentClass.getAnnotation(PageTitle.class).value();
            return getTranslation(key);
        }
        return "";
    }
}
