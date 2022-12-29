package kpn.ctrlf.client.i18n;

import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class I18nParamsTest {

	@ParameterizedTest
	@CsvFileSource(resources = "shouldCheckCalculationMethod_ifFail.csv")
	void shouldCheckCalculationMethod_ifFail(String baseName, String extension, String rawLocales, String code) {
		ImmutableResult<List<String>> expectedResult = ImmutableResult.<List<String>>fail(code);

		I18nParams params = new I18nParams();
		params.setBaseName(baseName);
		params.setExtension(extension);
		params.setLocales(rawLocales == null ? null : List.of(rawLocales.split("-")));

		Result<List<String>> result = params.calculatePaths();
		assertThat(result).isEqualTo(expectedResult);
	}

	@Test
	void shouldCheckCalculationMethod() {
		String baseName = "baseName";
		String extension = "ext";
		List<String> locales = List.of("ru", "en");

		ArrayList<String> paths = new ArrayList<>();
		for (String locale : locales) {
			paths.add(baseName + locale + "." + extension);
		}
		ImmutableResult<List<String>> expectedResult = ImmutableResult.<List<String>>ok(paths);

		I18nParams params = new I18nParams();
		params.setBaseName(baseName);
		params.setExtension(extension);
		params.setLocales(locales);

		Result<List<String>> result = params.calculatePaths();
		assertThat(result).isEqualTo(expectedResult);
	}
}
