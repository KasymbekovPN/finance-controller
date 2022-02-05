package kpn.financecontroller.data.propertyExtractors.checkers;

import kpn.financecontroller.result.Result;

// TODO: 02.02.2022 del
public class StringOnNullChecker implements Checker<String> {

    private String value;

    @Override
    public void set(String value) {
        this.value = value;
    }

    @Override
    public Result<String> check() {
        boolean success = value != null;
        return Result.<String>builder()
                .success(success)
                .value(value)
                .code(success ? "stringOnNullChecker.value.notNull" : "stringOnNullChecker.value.null")
                .build();
    }
}
