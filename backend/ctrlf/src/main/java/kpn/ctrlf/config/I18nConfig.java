package kpn.ctrlf.config;

import kpn.ctrlf.client.i18n.converter.I18nConverterImpl;
import kpn.ctrlf.client.i18n.params.I18nParamsImpl;
import kpn.ctrlf.client.i18n.I18nSource;
import kpn.ctrlf.client.i18n.reader.I18nReaderImpl;
import kpn.ctrlf.client.i18n.reader.ResourceFileReader;
import kpn.lib.result.Result;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "i18n")
public class I18nConfig {

	@Setter
	private I18nParamsImpl params;

	@Bean
	public I18nSource i18nSource() throws IOException {
		HashMap<String, Map<String, String>> templates = new HashMap<>();

		I18nReaderImpl reader = new I18nReaderImpl(new ResourceFileReader(), new I18nConverterImpl());
		Result<Map<String, String>> pathResult = params.calculate();
		if (pathResult.isSuccess()){
			for (Map.Entry<String, String> entry : pathResult.getValue().entrySet()) {
				String locale = entry.getKey();
				String path = entry.getValue();
				Result<Map<String, String>> readingResult = reader.get(path);
				if (readingResult.isSuccess()){
					log.info("Locale {} is loaded", locale);
					templates.put(locale, readingResult.getValue());
				} else {
					log.warn("Locale {}: {}", locale, readingResult.getSeed().getCode());
					for (int i = 0; i < readingResult.getSeed().getArgs().length; i++) {
						log.warn("\t{}: {}", i, readingResult.getSeed().getArgs()[i]);
					}
				}
			}
		} else {
			log.error("{}", pathResult.getSeed().getCode());
		}

		return new I18nSource(templates);
	}
}
