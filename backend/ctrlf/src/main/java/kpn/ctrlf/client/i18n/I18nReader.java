package kpn.ctrlf.client.i18n;

public interface I18nReader<T, R> {
	R get(T value);
}
