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
import kpn.financecontroller.gui.generators.RouterLinkGenerator;
import kpn.financecontroller.gui.view.*;
import kpn.financecontroller.gui.view.ByTagStatistic;
import kpn.financecontroller.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public final class MainLayout extends AppLayout {
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
            ActionEditor.class,
            ActionDisplay.class
    );

    private final H1 viewTitle = new H1();
    private final SecurityService securityService;
    private final RouterLinkGenerator<Class<? extends Component>> routerLinkGenerator;

    @Autowired
    public MainLayout(SecurityService securityService, RouterLinkGenerator<Class<? extends Component>> routerLinkGenerator) {
        this.securityService = securityService;
        this.routerLinkGenerator = routerLinkGenerator;
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        addToDrawer(createDrawerComponent());
    }

    private Component createHeaderContent() {
        DrawerToggle toggle = createAndCustomizeToggle();
        return createAndCustomizeHeader(toggle, viewTitle);
    }

    private Component createAndCustomizeHeader(DrawerToggle toggle, H1 viewTitle) {
        Button logout = new Button(getTranslation("gui.login.logout"), e -> securityService.logout());

        HorizontalLayout header = new HorizontalLayout(toggle, viewTitle, logout);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(viewTitle);
        header.setWidth("100%");

        return header;
    }

    private DrawerToggle createAndCustomizeToggle() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        toggle.getElement().setAttribute("aria-label", "Menu toggle");
        return toggle;
    }

    private Component createDrawerComponent() {
        H2 appName = new H2("FC");
        return new com.vaadin.flow.component.html.Section(appName, createNavigation(), new Footer());
    }

    private Component createNavigation() {
        Nav nav = new Nav();
        nav.getElement().setAttribute("aria-labelledby", "views");

        UnorderedList list = new UnorderedList();
        nav.add(list);

        for (Class<? extends Component> menuClass : MENU_CLASSES) {
            list.add(
                    new ListItem(
                            routerLinkGenerator.generate(menuClass)
                    )
            );
        }
        return nav;
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
