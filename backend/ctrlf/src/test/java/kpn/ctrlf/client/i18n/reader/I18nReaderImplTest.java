package kpn.ctrlf.client.i18n.reader;

import kpn.ctrlf.client.i18n.reader.I18nReaderImpl;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class I18nReaderImplTest {

	private static final String FILE_PATH = "file.path";
	private static final String CODE = "fail.code";
	private static final String FILE_CONTENT = "file.content";
	private static final Map<String, String> RESULT_VALUE = Map.of("key", "value");

	@Test
	void shouldCheckGetting_ifFileReturnsFailResult() {
		ImmutableResult<Map<String, String>> expectedResult = ImmutableResult.<Map<String,String>>bFail(CODE)
			.arg(FILE_PATH)
			.build();

		TestFileReader fileReader = createFailFileReader();
		I18nReaderImpl reader = new I18nReaderImpl(fileReader, null);
		Result<Map<String, String>> result = reader.get(FILE_PATH);

		assertThat(result).isEqualTo(expectedResult);
	}

	@Test
	void shouldCheckGetting() {
		ImmutableResult<Map<String, String>> expectedResult = ImmutableResult.<Map<String, String>>ok(RESULT_VALUE);

		TestFileReader fileReader = createFileReader();
		TestConverter converter = createConverter();
		Result<Map<String, String>> result = new I18nReaderImpl(fileReader, converter).get(FILE_PATH);

		assertThat(result).isEqualTo(expectedResult);
	}

	private TestFileReader createFailFileReader() {
		TestFileReader fileReader = Mockito.mock(TestFileReader.class);
		Mockito
			.when(fileReader.apply(FILE_PATH))
			.thenReturn(
				ImmutableResult.<String>bFail(CODE)
					.arg(FILE_PATH)
					.build()
			);

		return fileReader;
	}

	private TestFileReader createFileReader() {
		TestFileReader fileReader = Mockito.mock(TestFileReader.class);
		Mockito
			.when(fileReader.apply(FILE_PATH))
			.thenReturn(ImmutableResult.<String>ok(FILE_CONTENT));

		return fileReader;
	}

	private TestConverter createConverter() {
		TestConverter converter = Mockito.mock(TestConverter.class);

		Mockito
			.when(converter.apply(FILE_CONTENT))
			.thenReturn(ImmutableResult.<Map<String, String>>ok(RESULT_VALUE));

		return converter;
	}

	private abstract static class TestFileReader implements Function<String, Result<String>>{}
	private abstract static class TestConverter implements Function<String, Result<Map<String, String>>>{}
}
