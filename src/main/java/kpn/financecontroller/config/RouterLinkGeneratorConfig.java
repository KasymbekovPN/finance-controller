package kpn.financecontroller.config;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.RouterLink;
import kpn.financecontroller.gui.generators.ClassAliasGenerator;
import kpn.financecontroller.gui.generators.RouterLinkGeneratorImpl;
import kpn.financecontroller.gui.view.ActionDisplay;
import kpn.financecontroller.gui.view.ActionEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Configuration
public class RouterLinkGeneratorConfig {
    private final ClassAliasGenerator classAliasGenerator;

    public RouterLinkGeneratorConfig(ClassAliasGenerator classAliasGenerator) {
        this.classAliasGenerator = classAliasGenerator;
    }

    @Bean
    public RouterLinkGeneratorImpl routerLinkGenerator(){
        Map<Class<? extends Component>, Function<Class<? extends Component>, RouterLink>> map = new HashMap<>();
        map.put(ActionEditor.class, new ActionEditorCreator());
        map.put(ActionDisplay.class, new ActionDisplayCreator());
        return new RouterLinkGeneratorImpl(classAliasGenerator, map);
    }

    private static class ActionEditorCreator implements Function<Class<? extends Component>, RouterLink> {
        @Override
        public RouterLink apply(Class<? extends Component> aClass) {
            String key = String.valueOf(UUID.randomUUID());
            return new RouterLink("", aClass, new RouteParameters("UUID", key));
        }
    }

    private static class ActionDisplayCreator implements Function<Class<? extends Component>, RouterLink> {
        @Override
        public RouterLink apply(Class<? extends Component> aClass) {
            String key = String.valueOf(UUID.randomUUID());
            return new RouterLink("", aClass, new RouteParameters("UUID", key));
        }
    }
}
