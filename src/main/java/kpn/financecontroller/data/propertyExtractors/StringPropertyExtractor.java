package kpn.financecontroller.data.propertyExtractors;

import kpn.financecontroller.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class StringPropertyExtractor implements PropertyExtractor<String> {

    private final Environment environment;

    @Autowired
    public StringPropertyExtractor(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Result<String> extract(String property) {
        String value = environment.getProperty(property);
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
                .arg(property);
        if (success){
            builder
                    .value(value)
                    .arg(value);
        }

        return builder.build();
    }
}
