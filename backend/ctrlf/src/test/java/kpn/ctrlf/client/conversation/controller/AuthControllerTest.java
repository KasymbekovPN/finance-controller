package kpn.ctrlf.client.conversation.controller;

import kpn.ctrlf.client.conversation.controller.AuthController;
import kpn.ctrlf.data.domain.User;
import kpn.ctrlf.secure.UserSecureService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class AuthControllerTest {

	private static final String USER_NAME = "some.username";
	private static final String PASSWORD = "some.password";
	private static final String SESSION_ID = "some.sessionId";

	@Test
	void shouldCheckRequestUsernameGetter() {
		AuthController.Request request = (AuthController.Request) setField(new AuthController.Request(), "username", USER_NAME);
		assertThat(request.getUsername()).isEqualTo(USER_NAME);
	}

	@Test
	void shouldCheckRequestPasswordGetter() {
		AuthController.Request request = (AuthController.Request) setField(new AuthController.Request(), "password", PASSWORD);
		assertThat(request.getPassword()).isEqualTo(PASSWORD);
	}

	@Test
	void shouldCheckResponseSuccessGetter() {
		AuthController.Response response = new AuthController.Response(true, SESSION_ID, USER_NAME);
		assertThat(response.isSuccess()).isTrue();
	}

	@Test
	void shouldCheckResponseTokenGetter() {
		AuthController.Response response = new AuthController.Response(true, SESSION_ID, USER_NAME);
		assertThat(response.getToken()).isEqualTo(SESSION_ID);
	}

	@Test
	void shouldCheckResponseUsernameGetter() {
		AuthController.Response response = new AuthController.Response(true, SESSION_ID, USER_NAME);
		assertThat(response.getUsername()).isEqualTo(USER_NAME);
	}

	@Test
	void shouldCheckConverter() {
		User expectedUser = new User(USER_NAME, PASSWORD);

		AuthController.Request request = new AuthController.Request();
		setField(request, "username", USER_NAME);
		setField(request, "password", PASSWORD);

		AuthController.Converter converter = new AuthController.Converter();
		assertThat(converter.apply(request)).isEqualTo(expectedUser);
	}

	@Test
	void shouldCheckResponseMethod_ifCheckingNotPassed() {
		AuthController.Response expectedResponse = new AuthController.Response(false, SESSION_ID, USER_NAME);
		AuthController.Request request = new AuthController.Request();
		setField(request, "username", USER_NAME);
		setField(request, "password", PASSWORD);

		AuthController controller = new AuthController(createFailSecureService());
		AuthController.Response response = controller.response(SESSION_ID, request);

		assertThat(response).isEqualTo(expectedResponse);
	}

	@Test
	void shouldCheckResponseMethod_ifCheckingPassed() {
		AuthController.Response expectedResponse = new AuthController.Response(true, SESSION_ID, USER_NAME);
		AuthController.Request request = new AuthController.Request();
		setField(request, "username", USER_NAME);
		setField(request, "password", PASSWORD);

		AuthController controller = new AuthController(createSuccessSecureService());
		AuthController.Response response = controller.response(SESSION_ID, request);

		assertThat(response).isEqualTo(expectedResponse);
	}

	private Object setField(Object object, String name, String value) {
		Field field = null;
		try {
			field = object.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(object, value);
		} catch (NoSuchFieldException | IllegalAccessException ignored) {}

		return object;
	}

	private UserSecureService<User> createFailSecureService(){
		TextUserSecureService service = Mockito.mock(TextUserSecureService.class);
		Mockito
			.when(service.checkCredential(Mockito.anyObject()))
			.thenReturn(false);
		return  service;
	}

	private UserSecureService<User> createSuccessSecureService(){
		TextUserSecureService service = Mockito.mock(TextUserSecureService.class);
		Mockito
			.when(service.checkCredential(Mockito.anyObject()))
			.thenReturn(true);
		return  service;
	}

	private static abstract class TextUserSecureService implements UserSecureService<User> {}

}
