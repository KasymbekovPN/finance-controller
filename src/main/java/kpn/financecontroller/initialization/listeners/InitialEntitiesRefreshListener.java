package kpn.financecontroller.initialization.listeners;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Profile("dev")
@ConfigurationProperties(prefix = "initial.entities")
public class InitialEntitiesRefreshListener implements ApplicationListener<ContextRefreshedEvent> {

    @Setter
    private Boolean enable;
    @Setter
    private String directory;
    @Setter
    private List<ListItem> sequence;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (enable){
            log.info("DB init. data saving starting");
        }
    }

    @Setter
    @Getter
    public static class ListItem {
        private String key;
        private String path;
    }
}
