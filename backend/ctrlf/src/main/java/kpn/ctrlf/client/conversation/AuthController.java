package kpn.ctrlf.client.conversation;

import kpn.ctrlf.data.domain.User;
import kpn.ctrlf.secure.UserSecureService;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.function.Function;

@Controller
@RequiredArgsConstructor
public final class AuthController implements RequestController<AuthController.Request, AuthController.Response>{
	private final UserSecureService<User> userSecureService;

	// TODO: 04.02.2023 del
//	private final SimpMessagingTemplate template;

	@Override
	@MessageMapping("/authRequest/{sessionId}")
	@SendTo("/topic/authResponse/{sessionId}")
	public Response response(@DestinationVariable String sessionId, Request request) {
		// TODO: 04.02.2023 del
//		System.out.println(template);

		User user = new Converter().apply(request);
		boolean success = userSecureService.checkCredential(user);
		return new Response(success, sessionId, user.getUsername());
	}

	@Getter
	public final static class Request {
		private String username;
		private String password;
	}

	@RequiredArgsConstructor
	@Getter
	@EqualsAndHashCode
	public final static class Response {
		private final boolean success;
		private final String token;
		private final String username;
	}

	public final static class Converter implements Function<Request, User> {
		@Override
		public User apply(Request request) {
			return new User(request.getUsername(), request.getPassword());
		}
	}
}
