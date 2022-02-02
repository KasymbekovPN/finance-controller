package kpn.financecontroller.data.propertyExtractors.calculators;

import kpn.financecontroller.result.Result;
import lombok.AllArgsConstructor;

// TODO: 02.02.2022 replace
@AllArgsConstructor
public class SimplePathCalculator implements PathCalculator<String, String> {

    private final String base;

    @Override
    public Result<String> calculate(String value) {
        Result.Builder<String> builder = Result.<String>builder().success(false);
        if (base == null){
            builder.code("simplePathCalculator.base.null");
        } else {
            if (value == null){
                builder.code("simplePathCalculator.add.null");
            } else {
                builder
                        .success(true)
                        .value(base + "/" + value);
            }
        }

        return builder.build();
    }
}
