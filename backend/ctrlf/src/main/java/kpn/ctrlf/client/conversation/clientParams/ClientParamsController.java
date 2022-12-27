package kpn.ctrlf.client.conversation.clientParams;

import kpn.ctrlf.client.params.ClientParams;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ClientParamsController {

	private final ClientParams clientParams;

	@MessageMapping("/clientParamsRequest/{sessionId}")
	@SendTo("/topic/clientParamsResponse/{sessionId}")
	public Response response(@DestinationVariable String sessionId,
							 Request request){
		// TODO: 27.12.2022 del 
		//<
//		System.out.println("sessionId : " + sessionId);
//		System.out.println("request : " + request);
		//<
		return new Response(clientParams.getLocale());
	}

	public static class Request {}

	@RequiredArgsConstructor
	public static class Response {
		private final String locale;

		public String getLocale() {
			return locale;
		}
	}
}
