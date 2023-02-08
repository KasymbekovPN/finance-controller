package kpn.ctrlf.client.conversation.converter;

import kpn.ctrlf.client.conversation.response.args.DefaultErrorArgs;
import kpn.ctrlf.client.conversation.response.args.ErrorArgs;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultErrorArgsConverterTest {

	@Test
	void shouldCheckConversion() {
		ErrorArgs convert = new DefaultErrorArgsConverter().convert();
		assertThat(convert.getClass()).isEqualTo(DefaultErrorArgs.class);
	}
}
