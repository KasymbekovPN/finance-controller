package kpn.financecontroller.data.propertyExtractors;

import kpn.financecontroller.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IEPathsPropertyExtractor implements PropertyExtractor<List<String>> {

    private static final String PATHS = "initialEntities.paths";

    private final Environment environment;

    @Autowired
    public IEPathsPropertyExtractor(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Result<List<String>> extract() {
        String property = environment.getProperty(PATHS);
        String errorCode = "";
        List<String> value = null;
        if (property != null){
            String stripProperty = property.replaceAll("\\s+", "");
            if (stripProperty.isEmpty()){
                errorCode = "propertyFile.property.empty";
            } else {
                value = List.of(stripProperty.split(","));
            }
        } else {
            errorCode = "propertyFile.property.notExist";
        }

        Result.Builder<List<String>> builder = Result.<List<String>>builder();
        if (errorCode.isEmpty()){
            builder.success(true).value(value);
        } else {
            builder.success(false).code(errorCode).arg(PATHS);
        }

        return builder.build();
    }
}
