package kpn.financecontroller.initialization.setting;

import kpn.financecontroller.initialization.generators.valued.Entities;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "initial.entities")
public class SettingImpl implements Setting {
    @Setter @Getter
    private boolean enable;
    @Setter
    private String directory;

    @Override
    public String getPath(Entities entity) {
        return directory + "/" + entity.name().toLowerCase() + ".json";
    }
}
