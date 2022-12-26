package kpn.ctrlf.client.conversation.greeting;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class GreetingControllerTest {

	@Test
	void shouldCheckGreetingResponseMethod() {
		GreetingController.Response result
			= new GreetingController().greetingResponse(new GreetingController.Request());
		Assertions.assertThat(result.getClass()).isEqualTo(GreetingController.Response.class);
	}
}
