package kpn.ctrlf.client.i18n;

import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
public final class I18nSource {

	private final Map<String, Map<String, String>> templates;

	public Map<String, Map<String, String>> get() {
		return templates == null
			? Map.of()
			: Collections.unmodifiableMap(templates);
	}
}
