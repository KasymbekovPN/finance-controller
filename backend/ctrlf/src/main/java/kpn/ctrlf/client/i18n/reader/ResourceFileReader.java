package kpn.ctrlf.client.i18n.reader;

import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.nio.file.Files;
import java.util.function.Function;

public final class ResourceFileReader implements Function<String, Result<String>> {

	@Override
	public Result<String> apply(String path) {
		try {
			File file = new ClassPathResource(path).getFile();
			return ImmutableResult.<String>ok(Files.readString(file.toPath()));
		} catch (Throwable e) {
			return ImmutableResult.<String>bFail("resource-file-reader.exception")
				.arg(e.getMessage())
				.build();
		}
	}
}
