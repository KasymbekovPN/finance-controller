// TODO: 04.11.2022 del
//package kpn.financecontroller.config;
//
//import kpn.financecontroller.annotation.External;
//import kpn.financecontroller.data.services.action.ActionWorkerOld;
//import kpn.financecontroller.data.services.action.ActionWorkerOldImpl;
//import kpn.financecontroller.search.type.DeepClassSearcherByExternalAnnotation;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//// TODO: 29.10.2022 del
//@Slf4j
//@Configuration
//@ConfigurationProperties(prefix = "script.header")
//public class ActionWorkerConfig {
//    @Setter
//    private List<String> manualNames;
//    @Setter
//    private List<String> annotationPackages;
//
//    @Bean
//    public ActionWorkerOld actionWorker(){
//        Set<String> fullNames = createFromManualNames();
//        fullNames.addAll(createFromFromAnnotationPackages());
//        String header = createHeader(fullNames);
//        log.info("header:\n{}", header);
//        return new ActionWorkerOldImpl(header);
//    }
//
//    private Set<String> createFromManualNames() {
//        return new HashSet<>(manualNames);
//    }
//
//    private Set<String> createFromFromAnnotationPackages() {
//        Set<String> result = new HashSet<>();
//        DeepClassSearcherByExternalAnnotation searcher = new DeepClassSearcherByExternalAnnotation(External.class);
//        for (String annotationPackage : annotationPackages) {
//            List<Class<?>> classes = searcher.search(annotationPackage.replace(".", "/"));
//            for (Class<?> type : classes) {
//                result.add(type.getPackageName() + "." + type.getSimpleName());
//            }
//        }
//
//        return result;
//    }
//
//    private String createHeader(Set<String> fullNames) {
//        StringBuilder sb = new StringBuilder();
//        fullNames.forEach(p -> {
//                sb.append("import ").append(p).append(";\n");
//        });
//
//        return sb.toString();
//    }
//}
