package kpn.ctrlf.client.conversation.controller;

public interface RequestController<T, R> {
	R response(String sessionId, T request);
}
