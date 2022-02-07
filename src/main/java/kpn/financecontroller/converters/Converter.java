package kpn.financecontroller.converters;

public interface Converter<T, R> {
    R convert(T value);
}
