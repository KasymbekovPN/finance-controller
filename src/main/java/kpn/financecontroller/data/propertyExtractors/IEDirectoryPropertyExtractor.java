package kpn.financecontroller.data.propertyExtractors;

import kpn.financecontroller.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

// TODO: 05.02.2022 create through @config-file
@Component
@Profile("dev")
public class IEDirectoryPropertyExtractor implements SpecificPropertyExtractor<String> {

    private static final String PROPERTY = "initialEntities.directory";

    private final Environment environment;

    private Result<String> result;

    @Autowired
    public IEDirectoryPropertyExtractor(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Result<String> extract() {
        if (result == null){
            result = calculateResult();
        }
        return result;
    }

    private Result<String> calculateResult() {
        String value = environment.getProperty(PROPERTY);
        String code = "";
        boolean success = false;
        if (value != null){
            String stripValue = value.replaceAll("\\s+", "");
            if (stripValue.isEmpty()){
                code = "file.property.extraction.ItEmpty";
            } else {
                code = "file.property.extraction.success";
                success = true;
            }
        } else {
            code = "file.property.extraction.ItNotExist";
        }

        Result.Builder<String> builder = Result.<String>builder()
                .success(success)
                .code(code)
                .arg(PROPERTY);
        if (success){
            builder.value(value).arg(value);
        }
        return builder.build();
    }
}
