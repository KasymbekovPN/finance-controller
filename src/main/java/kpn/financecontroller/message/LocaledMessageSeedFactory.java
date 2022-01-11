package kpn.financecontroller.message;

import kpn.financecontroller.result.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Locale;

@Service
public class LocaledMessageSeedFactory {

    @Value("${message.seed.non-exist.code}")
    private String nonExistCode;
    @Value("${message.seed.non-exist.args}")
    private Object[] nonExistArgs;
    @Value("${message.seed.default-locale-code}")
    private String localeCode;

    private Locale defaultLocale;

    public LocaledMessageSeed create(Result<?> result, Locale locale) {
        return ResultMessageSeed.builder()
                .result(adjustResult(result))
                .locale(locale)
                .build();
    }

    public LocaledMessageSeed create(Result<?> result) {
        return create(result, getLocale());
    }

    private Locale getLocale() {
        if (defaultLocale == null){
            defaultLocale = new Locale(localeCode);
        }
        return defaultLocale;
    }

    private Result<Object> adjustResult(Result<?> result) {
        boolean codeIsNull = result.getCode() == null;
        Result.Builder<Object> builder = Result.<Object>builder()
                .code(codeIsNull ? nonExistCode : result.getCode());
        Arrays.stream(codeIsNull ? nonExistArgs : result.getArgs()).forEach(builder::arg);

        return builder.build();
    }
}
