package kpn.financecontroller.gui.generators;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "generators.alias.class")
public class ClassAliasGeneratorImpl implements ClassAliasGenerator {

    @Setter
    private String prefix;

    @Override
    public String generate(Class<?> type) {
        return prefix + type.getSimpleName();
    }
}
