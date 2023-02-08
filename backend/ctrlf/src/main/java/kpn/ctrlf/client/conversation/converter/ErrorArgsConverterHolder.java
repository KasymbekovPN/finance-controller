package kpn.ctrlf.client.conversation.converter;

import java.util.function.Supplier;

public interface ErrorArgsConverterHolder {
	ErrorArgsConverter take(String code);
	ErrorArgsConverterHolder set(String code, Supplier<ErrorArgsConverter> supplier);
	ErrorArgsConverterHolder erase(String code);
}
