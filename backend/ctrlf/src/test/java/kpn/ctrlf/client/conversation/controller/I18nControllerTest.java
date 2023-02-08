package kpn.ctrlf.client.conversation.controller;

import kpn.ctrlf.client.conversation.controller.I18nController;
import kpn.ctrlf.client.i18n.I18nSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class I18nControllerTest {

	@Test
	void shouldCheckResponseMethod() {
		Map<String, Map<String, String>> init = Map.of(
			"en", Map.of("arg", "value")
		);
		Map<String, Map<String, String>> expected = Map.of(
			"arg", Map.of("en", "value")
		);

		I18nSource source = I18nSource.create(init);
		I18nController.Response response = new I18nController(source).response("", new I18nController.Request());

		Assertions.assertThat(response.getTemplates()).isEqualTo(expected);
	}
}
