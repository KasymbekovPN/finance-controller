package kpn.financecontroller.converters;

// TODO: 27.02.2022 del ???
public interface Converter<T, R> {
    R convert(T value);
}
