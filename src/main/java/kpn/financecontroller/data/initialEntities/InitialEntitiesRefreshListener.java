package kpn.financecontroller.data.initialEntities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("dev")
public class InitialEntitiesRefreshListener implements ApplicationListener<ContextRefreshedEvent > {

    private static final String PATHS = "initialEntities.paths";

    private final Environment environment;

    public InitialEntitiesRefreshListener(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {


//        List<String> paths = calculatePaths();
//        // TODO: 30.01.2022 del 1
//        log.info("paths: {}", paths);




    }

//    private List<String> calculatePaths() {
//        String pathsLine = environment.getProperty(PATHS);
//        // TODO: 30.01.2022 del 1
//        log.info("pathsLine: '{}'", pathsLine);
//        return null;
//    }
}
