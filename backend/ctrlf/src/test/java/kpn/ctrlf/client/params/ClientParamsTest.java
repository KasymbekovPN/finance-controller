package kpn.ctrlf.client.params;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ClientParamsTest {

	@Test
	void shouldCheckGettingLocale() {
		String expectedLocale = "en";
		String locale = new ClientParams(expectedLocale).getLocale();

		Assertions.assertThat(locale).isEqualTo(expectedLocale);
	}
}
