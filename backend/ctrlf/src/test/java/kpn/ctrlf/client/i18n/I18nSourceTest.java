package kpn.ctrlf.client.i18n;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class I18nSourceTest {

	@Test
	void shouldCheckGetting_ifTemplatesIsNull() {
		Map<String, Map<String, String>> result = new I18nSource(null).get();

		assertThat(result).isEmpty();
	}

	@Test
	void shouldCheckGetting() {
		HashMap<String, String> value = new HashMap<>();
		value.put("arg", "value");
		HashMap<String, Map<String, String>> expectedResult = new HashMap<>();
		expectedResult.put("en", value);

		Map<String, Map<String, String>> result = new I18nSource(expectedResult).get();
		assertThat(result).isEqualTo(expectedResult);
	}

	@Test
	void shouldCheckGetting_resultMustBeUnmodifiable() {
		Throwable throwable = catchThrowable(() -> {
			Map<String, Map<String, String>> result = new I18nSource(null).get();
			result.put("en", Map.of());
		});
		assertThat(throwable).isInstanceOf(UnsupportedOperationException.class);
	}
}
