package kpn.financecontroller.gui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.gui.views.building.BuildingView;
import kpn.financecontroller.gui.views.city.CityView;
import kpn.financecontroller.gui.views.country.CountryView;
import kpn.financecontroller.gui.views.mainDetails.MainDetails;
import kpn.financecontroller.gui.views.product.ProductView;
import kpn.financecontroller.gui.views.region.RegionView;
import kpn.financecontroller.gui.views.street.StreetView;
import kpn.financecontroller.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

// TODO: 03.01.2022 translation
@PageTitle("Main")
public class MainLayout extends AppLayout {


    // TODO: 03.01.2022 refactoring
    public static class MenuItemInfo {

        private String text;
        private String iconClass;
        private Class<? extends Component> view;

        public MenuItemInfo(String text, String iconClass, Class<? extends Component> view) {
            this.text = text;
            this.iconClass = iconClass;
            this.view = view;
        }

        public String getText() {
            return text;
        }

        public String getIconClass() {
            return iconClass;
        }

        public Class<? extends Component> getView() {
            return view;
        }

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

//        Header header = new Header(toggle, viewTitle, logout);
        HorizontalLayout header = new HorizontalLayout(toggle, viewTitle, logout);
        // TODO: 03.01.2022 how set class names
//        header.addClassNames("bg-base", "border-b", "border-contrast-10", "box-border", "flex", "h-xl", "items-center",
//                "w-full");

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
        H2 appName = new H2("My App");
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

        // Wrap the links in a list; improves accessibility
        UnorderedList list = new UnorderedList();
        list.addClassNames("list-none", "m-0", "p-0");
        nav.add(list);

        for (RouterLink link : createLinks()) {
            ListItem item = new ListItem(link);
            list.add(item);
        }
        return nav;
    }

    // TODO: 03.01.2022 refact
    private List<RouterLink> createLinks() {
        MenuItemInfo[] menuItems = new MenuItemInfo[]{
                new MenuItemInfo("Main details", "la la-globe", MainDetails.class),
                new MenuItemInfo("Countries", "la la-globe", CountryView.class),
                new MenuItemInfo("Regions", "la la-globe", RegionView.class),
                new MenuItemInfo("Cities",  "la la-globe", CityView.class),
                new MenuItemInfo("Streets",  "la la-globe", StreetView.class),
                new MenuItemInfo("Buildings",  "la la-globe", BuildingView.class),
                new MenuItemInfo("Products",  "la la-globe", ProductView.class)
        };
        List<RouterLink> links = new ArrayList<>();
        for (MenuItemInfo menuItemInfo : menuItems) {
            links.add(createLink(menuItemInfo));

        }
        return links;
    }

    private static RouterLink createLink(MenuItemInfo menuItemInfo) {
        RouterLink link = new RouterLink();
        link.addClassNames("flex", "mx-s", "p-s", "relative", "text-secondary");
        link.setRoute(menuItemInfo.getView());

        Span icon = new Span();
        icon.addClassNames("me-s", "text-l");
        if (!menuItemInfo.getIconClass().isEmpty()) {
            icon.addClassNames(menuItemInfo.getIconClass());
        }

        Span text = new Span(menuItemInfo.getText());
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
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
