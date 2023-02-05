package kpn.ctrlf.client.conversation.tag;

import kpn.ctrlf.data.domain.Tag;
import kpn.ctrlf.fakes.FakeTagService;
import kpn.lib.result.ImmutableResult;
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
		TagCreationController.Response response = controller.response("", new TagCreationController.Request());

		ImmutableResult<Tag> expectedResult = ImmutableResult.<Tag>fail(CODE);
		assertThat(response.getResult()).isEqualTo(expectedResult);
	}

	@Test
	void shouldCheckTagSaving() {
		TagCreationController controller = new TagCreationController(createTagService());
		TagCreationController.Response response = controller.response("", new TagCreationController.Request());

		ImmutableResult<Tag> expectedResult = ImmutableResult.<Tag>ok(new Tag(ID, NAME));
		assertThat(response.getResult()).isEqualTo(expectedResult);
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
