package kpn.ctrlf.client.conversation;

public interface RequestController<T, R> {
	R response(String sessionId, T request);
}
