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
public final class AuthController {

	// TODO: 09.01.2023 FC-000178 add user-service

	@MessageMapping("/authRequest/{sessionId}")
	@SendTo("/topic/authResponse/{sessionId}")
	public Response response(@DestinationVariable String sessionId,
							 Request request){
		//<
		System.out.println("AuthController " + request.getUsername() + " " + request.getPassword());
		//<

		// TODO: 09.01.2023 it is temp. solution (FC-178)
		return new Response(true, sessionId, request.getUsername());
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
