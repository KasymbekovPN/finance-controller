package kpn.ctrlf.client.conversation.domain;

import kpn.ctrlf.client.conversation.domain.TagCreationController;
import kpn.ctrlf.data.domain.Tag;
import kpn.ctrlf.fakes.FakeTagService;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class TagCreationControllerTest {

	private static final String CODE = "some.code";
	private static final String NAME = "some.name";
	private static final Long ID = 123L;

	@Test
	void shouldCheckTagSaving_ifFail() {
		TagCreationController controller = new TagCreationController(createFailTagService());
		Result<Tag> response = controller.response("", new TagCreationController.Request());

		ImmutableResult<Tag> expectedResult = ImmutableResult.<Tag>fail(CODE);
		assertThat(response).isEqualTo(expectedResult);
	}

	@Test
	void shouldCheckTagSaving() {
		TagCreationController controller = new TagCreationController(createTagService());
		Result<Tag> response = controller.response("", new TagCreationController.Request());

		ImmutableResult<Tag> expectedResult = ImmutableResult.<Tag>ok(new Tag(ID, NAME));
		assertThat(response).isEqualTo(expectedResult);
	}

	private FakeTagService createFailTagService() {
		TestFakeTagService service = Mockito.mock(TestFakeTagService.class);
		Mockito
			.when(service.save(Mockito.anyObject()))
			.thenReturn(ImmutableResult.<Tag>fail(CODE));

		return service;
	}

	private FakeTagService createTagService() {
		TestFakeTagService service = Mockito.mock(TestFakeTagService.class);
		Mockito
			.when(service.save(Mockito.anyObject()))
			.thenReturn(ImmutableResult.<Tag>ok(new Tag(ID, NAME)));

		return service;
	}

	private abstract static class TestFakeTagService implements FakeTagService {}
}
