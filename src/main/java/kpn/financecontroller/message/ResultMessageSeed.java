package kpn.financecontroller.message;

import kpn.financecontroller.result.Result;
import lombok.Getter;

import java.util.Locale;

@Getter
public class ResultMessageSeed implements LocaledMessageSeed {
    private final String code;
    private final Locale locale;
    private final Object[] args;

    public static Builder builder(){
        return new Builder();
    }

    private ResultMessageSeed(String code, Locale locale, Object[] args) {
        this.code = code;
        this.locale = locale;
        this.args = args;
    }

    public static class Builder{
        private String code;
        private Locale locale;
        private Object[] args;

        public Builder result(Result<?> result){
            code = result.getCode();
            args = result.getArgs();
            return this;
        }

        public Builder locale(Locale locale){
            this.locale = locale;
            return this;
        }

        public ResultMessageSeed build(){
            return new ResultMessageSeed(code, locale, args);
        }
    }
}
