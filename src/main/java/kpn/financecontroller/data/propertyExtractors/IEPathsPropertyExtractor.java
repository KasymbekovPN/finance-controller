package kpn.financecontroller.data.propertyExtractors;

import kpn.financecontroller.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

// TODO: 30.01.2022 del
@Component
@Profile("dev")
public class IEPathsPropertyExtractor implements PropertyExtractor<List<String>> {

    private static final String PATHS = "initialEntities.paths";

    private final Environment environment;

    @Autowired
    public IEPathsPropertyExtractor(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Result<List<String>> extract(String property) {
        String property1 = environment.getProperty(PATHS);
        String code = "";
        List<String> value = null;
        boolean success = false;
        if (property1 != null){
            String stripProperty = property1.replaceAll("\\s+", "");
            if (stripProperty.isEmpty()){
                code = "file.property.extraction.ItEmpty";
            } else {
                code = "file.property.extraction.success";
                value = List.of(stripProperty.split(","));
                success = true;
            }
        } else {
            code = "file.property.extraction.ItNotExist";
        }

        Result.Builder<List<String>> builder = Result.<List<String>>builder()
                .success(success)
                .code(code)
                .arg(PATHS);
        if (success){
            builder
                    .value(value)
                    .arg(value);
        }

        return builder.build();
    }
}
