package kpn.ctrlf.config;

import kpn.ctrlf.client.i18n.I18nParamsImpl;
import kpn.ctrlf.client.i18n.I18nSource;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

// TODO: 28.12.2022 test
@Configuration
@ConfigurationProperties(prefix = "i18n")
public class I18nConfig {

	@Setter
	private I18nParamsImpl params;

	@Bean
	public I18nSource i18nSource() throws IOException {
//		if (checkParams()){
//			for (String locale : locales) {
//				String filePath = createFilePath(locale);
//				String content = readFile(filePath);
//
//				// TODO: 28.12.2022 create map from text through spec instance
//			}
//		}

		// TODO: 28.12.2022 del
//		File file1 = new ClassPathResource("i18n_en.properties").getFile();
//		String s = new String(Files.readAllBytes(file1.toPath()));

		return  new I18nSource();
	}
}
