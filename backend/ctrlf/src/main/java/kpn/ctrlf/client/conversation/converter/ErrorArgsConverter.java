package kpn.ctrlf.client.conversation.converter;

import kpn.ctrlf.client.conversation.response.args.ErrorArgs;

public interface ErrorArgsConverter {
	ErrorArgs convert(Object... args);
}
