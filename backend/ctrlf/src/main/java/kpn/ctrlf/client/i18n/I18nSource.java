package kpn.ctrlf.client.i18n;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class I18nSource {

	public static I18nSource create(Map<String, Map<String, String>> templates){
		HashMap<String, Map<String, String>> ts = new HashMap<>();

		if (templates != null){
			for (Map.Entry<String, Map<String, String>> entry : templates.entrySet()) {
				String locale = entry.getKey();

				Map<String, String> codes = entry.getValue();
				for (Map.Entry<String, String> codeEntry : codes.entrySet()) {
					String code = codeEntry.getKey();
					String template = codeEntry.getValue();

					if (!ts.containsKey(code)){
						ts.put(code, new HashMap<>());
					}
					ts.get(code).put(locale, template);
				}
			}
		}

		return new I18nSource(ts);
	}

	private final Map<String, Map<String, String>> templates;

	private I18nSource(Map<String, Map<String, String>> templates) {
		this.templates = templates;
	}

	public Map<String, Map<String, String>> get() {
		if (templates == null){
			return Map.of();
		}

		HashMap<String, Map<String, String>> ts = new HashMap<>();
		for (Map.Entry<String, Map<String, String>> entry : templates.entrySet()) {
			ts.put(entry.getKey(), Collections.unmodifiableMap(entry.getValue()));
		}

		return  Collections.unmodifiableMap(ts);
	}
}
