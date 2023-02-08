package kpn.ctrlf.client.conversation.converter;

import kpn.ctrlf.client.conversation.response.args.DefaultErrorArgs;
import kpn.ctrlf.client.conversation.response.args.ErrorArgs;

public class DefaultErrorArgsConverter implements ErrorArgsConverter {
	@Override
	public ErrorArgs convert(Object... args) {
		return new DefaultErrorArgs();
	}
}
