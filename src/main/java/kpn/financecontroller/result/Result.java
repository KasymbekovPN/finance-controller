package kpn.financecontroller.result;

import kpn.financecontroller.message.MessageSeed;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Result<T> implements MessageSeed {
    private final Boolean success;
    private final T value;
    private final String code;
    private final Object[] args;

    public static <T> Builder<T> builder(){
        return new Builder<>();
    }

    private Result(boolean success, T value, String code, Object[] args) {
        this.success = success;
        this.value = value;
        this.code = code;
        this.args = args;
    }

    public static class Builder<T>{
        private boolean success = false;
        private T value;
        private String code;
        private final List<Object> args = new ArrayList<>();

        public Builder<T> success(boolean success){
            this.success = success;
            return this;
        }

        public Builder<T> value(T value){
            this.value = value;
            return this;
        }

        public Builder<T> code(String code){
            this.code = code;
            return this;
        }

        public Builder<T> arg(Object arg){
            args.add(arg);
            return this;
        }

        public Result<T> build(){
            return new Result<>(success, value, code, args.toArray());
        }
    }
}
