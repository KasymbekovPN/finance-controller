package kpn.ctrlf.client.i18n.reader;

public interface I18nReader<T, R> {
	R get(T value);
}
