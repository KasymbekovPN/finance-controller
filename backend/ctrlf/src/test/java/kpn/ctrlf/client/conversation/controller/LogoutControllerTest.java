package kpn.ctrlf.client.conversation.controller;

import kpn.ctrlf.client.conversation.controller.LogoutController;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LogoutControllerTest {

	@Test
	void shouldCheckResponseIsSuccessMethod_ifFalse() {
		LogoutController.Response response = new LogoutController.Response(false);
		assertThat(response.isSuccess()).isFalse();
	}

	@Test
	void shouldCheckResponseIsSuccessMethod_ifTrue() {
		LogoutController.Response response = new LogoutController.Response(true);
		assertThat(response.isSuccess()).isTrue();
	}

	@Test
	void shouldCheckResponseMethod() {
		LogoutController.Response response = new LogoutController().response("", new LogoutController.Request());
		assertThat(response.isSuccess()).isTrue();
	}
}
