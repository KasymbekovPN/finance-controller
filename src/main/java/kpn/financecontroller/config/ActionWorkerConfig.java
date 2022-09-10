package kpn.financecontroller.config;

import kpn.financecontroller.annotation.External;
import kpn.financecontroller.data.services.action.ActionWorker;
import kpn.financecontroller.data.services.action.ActionWorkerImpl;
import kpn.financecontroller.search.type.DeepClassSearcherByExternalAnnotation;
import kpn.financecontroller.search.type.DeepImportDataSearcher;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "external")
public class ActionWorkerConfig {
    @Setter
    private String packageName;

    @Bean
    public ActionWorker actionWorker(){
        return new ActionWorkerImpl(createHeader());
    }

    private String createHeader() {
        DeepImportDataSearcher searcher = new DeepImportDataSearcher(new DeepClassSearcherByExternalAnnotation(External.class));
        return searcher.search(packageName);
    }
}
