package kpn.ctrlf.client.conversation.converter.domain.tag;

import kpn.ctrlf.client.conversation.response.args.ErrorArgs;
import kpn.ctrlf.client.conversation.response.args.TagCreationErrorArgs;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TagCreationErrorArgsConverterTest {

	@Test
	void shouldCheckConversion_ifInputArgsHaveZeroLen() {
		TagCreationErrorArgsConverter converter = new TagCreationErrorArgsConverter();

		ErrorArgs args = converter.convert();
		assertThat(args.getClass()).isEqualTo(TagCreationErrorArgs.class);
		assertThat(((TagCreationErrorArgs) args).getName()).isNull();
	}

	@Test
	void shouldCheckConversion() {
		String expectedName = "some.name";
		TagCreationErrorArgsConverter converter = new TagCreationErrorArgsConverter();

		ErrorArgs args = converter.convert(expectedName);
		assertThat(args.getClass()).isEqualTo(TagCreationErrorArgs.class);
		assertThat(((TagCreationErrorArgs) args).getName()).isEqualTo(expectedName);
	}
}
