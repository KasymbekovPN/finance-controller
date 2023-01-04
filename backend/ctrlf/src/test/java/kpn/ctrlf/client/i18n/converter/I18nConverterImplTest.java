package kpn.ctrlf.client.i18n.converter;

import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class I18nConverterImplTest {

	private static final String WRONG_ONLY_CONTENT =
		"""
        hello
        world
        """;

	private static final String CONTENT_WITH_WRONG_MIXIN =
        """
        arg0 =   value0
        hello
        world
           arg1= value1
        """;

	private static final String CONTENT =
  		"""
		arg0=value0
		arg1=value1

		arg2=value2
		arg3=value3 // comment
		// arg4=value4

		/*
		arg5=value5
		*/

		arg6=value6
		""";

	@Test
	void shouldCheckConversion_ifContentIsNull() {
		ImmutableResult<Map<String, String>> expectedResult
			= ImmutableResult.<Map<String, String>>fail("i18n.converter.content.null");

		Result<Map<String, String>> result = new I18nConverterImpl().apply(null);
		assertThat(result).isEqualTo(expectedResult);
	}

	@Test
	void shouldCheckConversion_ifContentIsBlank() {
		ImmutableResult<Map<String, String>> expectedResult
			= ImmutableResult.<Map<String, String>>fail("i18n.converter.content.blank");

		Result<Map<String, String>> result = new I18nConverterImpl().apply("  ");
		assertThat(result).isEqualTo(expectedResult);
	}

	@Test
	void shouldCheckConversion_ifContentIsWrongOnly() {
		ImmutableResult<Map<String, String>> expectedResult
			= ImmutableResult.<Map<String, String>>fail("i18n.converter.content.no-valuable");

		Result<Map<String, String>> result = new I18nConverterImpl().apply(WRONG_ONLY_CONTENT);
		assertThat(result).isEqualTo(expectedResult);
	}

	@Test
	void shouldCheckConversion_ifContentContainsWrongPart() {
		ImmutableResult<Map<String, String>> expectedResult
			= ImmutableResult.<Map<String, String>>ok(Map.of("arg0", "value0", "arg1", "value1"));

		Result<Map<String, String>> result = new I18nConverterImpl().apply(CONTENT_WITH_WRONG_MIXIN);
		assertThat(result).isEqualTo(expectedResult);
	}

	@Test
	void shouldCheckConversion() {
		ImmutableResult<Map<String, String>> expectedResult = ImmutableResult.<Map<String, String>>ok(Map.of(
			"arg0", "value0",
			"arg1", "value1",
			"arg2", "value2",
			"arg3", "value3",
			"arg6", "value6"
		));

		Result<Map<String, String>> result = new I18nConverterImpl().apply(CONTENT);
		assertThat(result).isEqualTo(expectedResult);
	}
}
