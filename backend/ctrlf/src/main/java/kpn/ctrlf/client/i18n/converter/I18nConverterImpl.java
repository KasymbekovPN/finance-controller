package kpn.ctrlf.client.i18n.converter;

import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class I18nConverterImpl implements Function<String, Result<Map<String, String>>> {
	private static final String COMMENT_CLEARING_REGEX = "//.*|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/";

	@Override
	public Result<Map<String, String>> apply(String content) {
		if (content == null){
			return ImmutableResult.<Map<String, String>>fail("i18n.converter.content.null");
		}

		String clearedContent = content.replaceAll(COMMENT_CLEARING_REGEX, "");
		if (clearedContent.isBlank()){
			return ImmutableResult.<Map<String, String>>fail("i18n.converter.content.blank");
		}

		HashMap<String, String> resultValue = new HashMap<>();
		List<String> lines = List.of(clearedContent.split("\n"));

		for (String line : lines) {
			int idx = line.indexOf('=');
			if (idx != -1) {
				resultValue.put(
					line.substring(0, idx).strip(),
					line.substring(idx + 1).strip()
				);
			}
		}

		return resultValue.isEmpty()
			? ImmutableResult.<Map<String, String>>fail("i18n.converter.content.no-valuable")
			: ImmutableResult.<Map<String, String>>ok(resultValue);
	}
}
