package kpn.financecontroller.config;

import kpn.financecontroller.gui.binding.util.Binder;
import kpn.financecontroller.gui.binding.util.BinderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActionEditorBinderConfig {

    @Bean
    public Binder<String, Integer> uuidToEditorBinder(){
        return new BinderImpl<>();
    }

    @Bean
    public Binder<Long, String> actionIdToUuidBinder(){
        return new BinderImpl<>();
    }
}
