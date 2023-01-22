package kpn.ctrlf.client.conversation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

// TODO: 09.01.2023 FC-000178 add test
@Controller
@RequiredArgsConstructor
public final class AuthController implements RequestController<AuthController.Request, AuthController.Response>{
	// TODO: 09.01.2023 FC-000178 add user-service

	@Override
	@MessageMapping("/authRequest/{sessionId}")
	@SendTo("/topic/authResponse/{sessionId}")
	public Response response(@DestinationVariable String sessionId, Request request) {
		// TODO: 09.01.2023 it is temp. solution (FC-178)
		boolean success = request.getUsername().equals("admin") && request.getPassword().equals("admin");
		return new Response(success, sessionId, request.getUsername());
	}

	@Getter
	public final static class Request {
		private String username;
		private String password;
	}

	@RequiredArgsConstructor
	@Getter
	public final static class Response {
		private final boolean success;
		private final String token;
		private final String username;
	}
}
