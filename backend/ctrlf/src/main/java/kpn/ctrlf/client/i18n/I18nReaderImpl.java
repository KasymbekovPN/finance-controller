package kpn.ctrlf.client.i18n;

import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import kpn.lib.seed.Seed;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
public final class I18nReaderImpl implements I18nReader<String, Result<Map<String, String>>> {
	private final Function<String, Result<String>> fileReader;
	private final Function<String, Result<Map<String, String>>> converter;

	@Override
	public Result<Map<String, String>> get(String value) {
		Result<String> fileReadingResult = fileReader.apply(value);
		if (fileReadingResult.isSuccess()){
			return converter.apply(fileReadingResult.getValue());
		}

		Seed seed = fileReadingResult.getSeed();
		ImmutableResult.Builder<Map<String, String>> builder
			= ImmutableResult.<Map<String, String>>bFail(seed.getCode());
		Arrays.stream(seed.getArgs()).forEach(builder::arg);

		return builder.build();
	}
}
