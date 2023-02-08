package kpn.ctrlf.client.conversation.response.args;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public final class TagCreationErrorArgs implements ErrorArgs {
	private final String name;
}
