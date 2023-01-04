package kpn.ctrlf.client.conversation.i18n;

import kpn.ctrlf.client.i18n.I18nSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class I18nControllerTest {

	@Test
	void shouldCheckResponseMethod() {
		Map<String, Map<String, String>> templates = Map.of(
			"en", Map.of("arg", "value")
		);

		I18nSource source = new I18nSource(templates);
		I18nController.Response response = new I18nController(source).response("", new I18nController.Request());

		Assertions.assertThat(response.getTemplates()).isEqualTo(templates);
	}
}
