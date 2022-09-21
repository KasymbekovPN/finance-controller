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
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.Router;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import kpn.financecontroller.gui.generators.ClassAliasGenerator;
import kpn.financecontroller.gui.view.*;
import kpn.financecontroller.gui.view.ByTagStatistic;
import kpn.financecontroller.security.SecurityService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

final public class MainLayout extends AppLayout {

    private static final List<Class<? extends Component>> MENU_CLASSES = List.of(
            PaymentView.class,
            ProductView.class,
            TagView.class,
            SellerView.class,
            AddressView.class,
            StreetView.class,
            CityView.class,
            RegionView.class,
            CountryView.class,
            ActionView.class,
            ByTagStatistic.class,
            ActionEditor.class
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
        // TODO: 19.09.2022 del
        System.out.println("MainLayout constructor");
        VaadinSession session = VaadinSession.getCurrent();
        System.out.println(session);

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
//        System.out.println("type: " + type);// TODO: 21.09.2022 del
        String text = classAliasGenerator.generate(type);
//        System.out.println("text: " + text); // TODO: 21.09.2022 del
        return new MenuItemInfo(text, "la la-globe", type);
    }

    private RouterLink createLink(MenuItemInfo menuItemInfo) {
        RouterLink link = new RouterLink();
        link.addClassNames("flex", "mx-s", "p-s", "relative", "text-secondary");

        // TODO: 21.09.2022 del
//        link.setRoute(menuItemInfo.getView());

        if (menuItemInfo.getView() != ActionEditor.class){
            link.setRoute(menuItemInfo.getView());
        } else {
            // TODO: 21.09.2022 random edition session 
            link = new RouterLink("editor", menuItemInfo.getView(), new RouteParameters("ID", "123"));
            link.addClassNames("flex", "mx-s", "p-s", "relative", "text-secondary");
//            Router router = new Router();
//            link.setRoute(router, menuItemInfo.getView());
        }

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
