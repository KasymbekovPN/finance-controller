package kpn.ctrlf.client.conversation.controller.domain;

import kpn.ctrlf.client.conversation.controller.RequestController;
import kpn.ctrlf.data.domain.Tag;
import kpn.ctrlf.fakes.FakeTagService;
import kpn.lib.result.Result;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public final class TagCreationController implements RequestController<TagCreationController.Request, Result<Tag>> {

	private final FakeTagService tagService;

	@Override
	@MessageMapping("/tagCreationRequest/{sessionId}")
	@SendTo("/topic/tagCreationResponse/{sessionId}")
	public Result<Tag> response(@DestinationVariable String sessionId, Request request) {
		return tagService.save(new Tag(null, request.getName()));
	}

	@Getter
	@Setter
	public final static class Request {
		private String name;
	}
}
