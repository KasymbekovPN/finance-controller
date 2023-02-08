package kpn.ctrlf.client.conversation.controller;

import kpn.ctrlf.client.conversation.controller.ClientParamsController;
import kpn.ctrlf.client.params.ClientParams;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientParamsControllerTest {

	@Test
	void shouldCheckResponseLocaleGetting() {
		String expectedLocale = "en";
		String locale = new ClientParamsController.Response(expectedLocale).getLocale();

		assertThat(locale).isEqualTo(expectedLocale);
	}

	@Test
	void shouldCheckResponseMethod() {
		String expectedLocale = "en";
		ClientParams clientParams = new ClientParams(expectedLocale);
		ClientParamsController.Response response
			= new ClientParamsController(clientParams).response("", new ClientParamsController.Request());

		assertThat(response.getClass()).isEqualTo(ClientParamsController.Response.class);
		assertThat(response.getLocale()).isEqualTo(expectedLocale);
	}
}
