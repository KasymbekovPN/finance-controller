package kpn.ctrlf.client.conversation.converter.domain.tag;

import kpn.ctrlf.client.conversation.converter.ErrorArgsConverter;
import kpn.ctrlf.client.conversation.response.args.ErrorArgs;
import kpn.ctrlf.client.conversation.response.args.TagCreationErrorArgs;

public final class TagCreationErrorArgsConverter implements ErrorArgsConverter {
	@Override
	public ErrorArgs convert(Object... args) {
		return args.length > 0
			? TagCreationErrorArgs.builder().name(String.valueOf(args[0])).build()
			: TagCreationErrorArgs.builder().build();
	}
}
