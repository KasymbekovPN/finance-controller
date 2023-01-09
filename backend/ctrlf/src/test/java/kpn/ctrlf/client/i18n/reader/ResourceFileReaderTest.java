package kpn.ctrlf.client.i18n.reader;

import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceFileReaderTest {

	private static final String CODE_ON_EXCEPTION = "resource-file-reader.exception";

	@Test
	void shouldCheckReading_exception() {
		ResourceFileReader reader = new ResourceFileReader();
		Result<String> result = reader.apply(null);

		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.getSeed().getCode()).isEqualTo(CODE_ON_EXCEPTION);
	}

	@Test
	void shouldCheckReading_fileDoesNotExist() {
		String path = getCalculateFilePath("nonExist.txt");

		Result<String> result = new ResourceFileReader().apply(path);
		assertThat(result).isNotNull();
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.getSeed().getCode()).isEqualTo(CODE_ON_EXCEPTION);
	}

	@Test
	void shouldCheckReading() {
		ImmutableResult<String> expectedResult = ImmutableResult.<String>ok("content\r\n");

		String path = getCalculateFilePath("exist.txt");

		Result<String> result = new ResourceFileReader().apply(path);
		assertThat(result).isEqualTo(expectedResult);
	}

	private String getCalculateFilePath(String fileName){
		String packageName = getClass().getPackageName();
		return "/" + packageName.replace(".", "/") + "/" + fileName;
	}
}
