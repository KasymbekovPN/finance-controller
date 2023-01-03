package kpn.ctrlf.client.i18n;

import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class I18nParamsImplTest {

	@ParameterizedTest
	@CsvFileSource(resources = "shouldCheckCalculationMethod_ifFail.csv")
	void shouldCheckCalculationMethod_ifFail(String baseName, String extension, String rawLocales, String code) {
		ImmutableResult<List<String>> expectedResult = ImmutableResult.<List<String>>fail(code);

		I18nParamsImpl params = new I18nParamsImpl();
		params.setBaseName(baseName);
		params.setExtension(extension);
		params.setLocales(rawLocales == null ? null : List.of(rawLocales.split("-")));

		Result<Map<String, String>> result = params.calculate();
		assertThat(result).isEqualTo(expectedResult);
	}

	@Test
	void shouldCheckCalculationMethod() {
		String baseName = "baseName";
		String extension = "ext";
		List<String> locales = List.of("ru", "en");

		HashMap<String, String> paths = new HashMap<>();
		for (String locale : locales) {
			paths.put(locale, baseName + locale + "." + extension);
		}
		ImmutableResult<Map<String, String>> expectedResult = ImmutableResult.<Map<String, String>>ok(paths);

		I18nParamsImpl params = new I18nParamsImpl();
		params.setBaseName(baseName);
		params.setExtension(extension);
		params.setLocales(locales);

		Result<Map<String, String>> result = params.calculate();
		assertThat(result).isEqualTo(expectedResult);
	}
}
