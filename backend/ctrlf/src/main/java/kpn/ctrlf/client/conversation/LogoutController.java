package kpn.ctrlf.client.conversation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public final class LogoutController implements RequestController<LogoutController.Request, LogoutController.Response> {

	@Override
	@MessageMapping("/logoutRequest/{sessionId}")
	@SendTo("/topic/logoutResponse/{sessionId}")
	public Response response(@DestinationVariable String sessionId,
							 Request request){
		return new Response(true);
	}

	public static class Request {}

	@RequiredArgsConstructor
	@Getter
	public static class Response {
		private final boolean success;
	}
}
