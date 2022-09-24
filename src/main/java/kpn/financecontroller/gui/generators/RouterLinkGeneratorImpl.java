package kpn.financecontroller.gui.generators;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.RouterLink;

import java.util.Map;
import java.util.function.Function;

@Tag(Tag.OBJECT)
public class RouterLinkGeneratorImpl extends Component implements RouterLinkGenerator<Class<? extends Component>> {
    private static final String[] LINK_CLASSES = new String[]{"flex", "mx-s", "p-s", "relative", "text-secondary"};
    private static final String[] TEXT_CLASSES = new String[]{"font-medium", "text-s"};

    private final ClassAliasGenerator classAliasGenerator;
    private final Map<Class<? extends Component>, Function<Class<? extends Component>, RouterLink>> routeLinkCreators;

    public RouterLinkGeneratorImpl(ClassAliasGenerator classAliasGenerator,
                                   Map<Class<? extends Component>, Function<Class<? extends Component>, RouterLink>> routeLinkCreators) {
        this.classAliasGenerator = classAliasGenerator;
        this.routeLinkCreators = routeLinkCreators;
    }

    @Override
    public RouterLink generate(Class<? extends Component> aClass) {
        RouterLink routerLink = createLink(aClass);
        routerLink.add(createText(aClass));
        routerLink.addClassNames(LINK_CLASSES);
        return routerLink;
    }

    private RouterLink createLink(Class<? extends Component> aClass) {
        if (routeLinkCreators.containsKey(aClass)){
            return routeLinkCreators.get(aClass).apply(aClass);
        } else {
            return createDefaultLink(aClass);
        }
    }

    private RouterLink createDefaultLink(Class<? extends Component> aClass) {
        RouterLink link = new RouterLink();
        link.setRoute(aClass);
        return link;
    }

    private Component createText(Class<?> aClass) {
        Span text = new Span(getTranslation(classAliasGenerator.generate(aClass)));
        text.addClassNames(TEXT_CLASSES);
        return text;
    }
}
