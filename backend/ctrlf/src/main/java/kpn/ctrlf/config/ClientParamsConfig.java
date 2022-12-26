package kpn.ctrlf.config;

import kpn.ctrlf.client.params.ClientParams;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "client")
public class ClientParamsConfig {
	@Setter
	private String locale;

	@Bean
	public ClientParams clientParams(){
		return new ClientParams(locale);
	}
}
