package kpn.ctrlf.client.greeting;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

	@MessageMapping("/greetingRequest")
	@SendTo("/topic/greetingResponse")
	public GreetingResponse greetingResponse(GreetingRequest request){
		return new GreetingResponse();
	}

	public static class GreetingRequest {}
	public static class GreetingResponse {}
}
