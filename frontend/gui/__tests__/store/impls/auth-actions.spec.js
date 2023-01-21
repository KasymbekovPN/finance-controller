import { requestLogin, requestLogout, responseLogin, responseLogout } from "../../../src/store/imps/auth-actions";
import { DESTINATIONS } from "../../../src/sconst/destinations";
import { AUTH_LOGIN_ERROR, AUTH_LOGIN_REQUEST, AUTH_LOGIN_SUCCESS, AUTH_LOGOUT_ERROR, AUTH_LOGOUT_REQUEST, AUTH_LOGOUT_SUCCESS } from "../../../src/store/sconst/auth";
import { CONNECTION_SEND } from "../../../src/store/sconst/connection";
import { USER_PROFILE_RESET, USER_PROFILE_SET } from "../../../src/store/sconst/userProfile";
import { PATHS } from "../../../src/sconst/path";

describe('auth-actions.js', () => {

	let commitResult;
	const commit = (command, data) => {
		commitResult = data !== undefined ? { command, data } : { command };
	};
	let dispatchResult;
	const dispatch = (command, data) => {
		dispatchResult = data !== undefined ? { command, data } : { command };
	};

	class Router {
		push (path) {
			this.path = path;
		}
	}

	const reset = () => {
		dispatchResult = commitResult = undefined;
	};

	test('should check requestLogin-actions', () => {
		const user = {data: 'user'};
		const expectedCommitResult = {command: AUTH_LOGIN_REQUEST};
		const expectedDispatchResult = {
			command: CONNECTION_SEND,
			data: {
				destination: DESTINATIONS.auth,
				headers: {},
				body: user
			}
		};

		requestLogin({commit, dispatch}, user);
		expect(commitResult).toStrictEqual(expectedCommitResult);
		expect(dispatchResult).toStrictEqual(expectedDispatchResult);
		reset();
	});

	test('should check responseLogin-actions if response is fail', () => {
		const response = {success: false};
		const expectedCommitResult = {command: AUTH_LOGIN_ERROR};
		const expectedDispatchResult = {
			command: USER_PROFILE_SET,
			data: response
		};
		const router = new Router();

		responseLogin({commit, dispatch, router}, response);
		expect(commitResult).toStrictEqual(expectedCommitResult);
		expect(dispatchResult).toStrictEqual(expectedDispatchResult);
		expect(router.path).toBe(PATHS.home);
		reset();
	});

	test('should check responseLogin-actions', () => {
		const response = {success: true};
		const expectedCommitResult = {command: AUTH_LOGIN_SUCCESS, data: response};
		const expectedDispatchResult = {
			command: USER_PROFILE_SET,
			data: response
		};
		const router = new Router();

		responseLogin({commit, dispatch, router}, response);
		expect(commitResult).toStrictEqual(expectedCommitResult);
		expect(dispatchResult).toStrictEqual(expectedDispatchResult);
		expect(router.path).toBe(PATHS.home);
		reset();
	});


	test('should check requestLogout-actions', () => {
		const expectedCommitResult = {command: AUTH_LOGOUT_REQUEST};
		const expectedDispatchResult = {
			command: CONNECTION_SEND,
			data: {
				destination: DESTINATIONS.logout,
				headers: {},
				body: {}
			}
		};

		requestLogout({commit, dispatch});
		expect(commitResult).toStrictEqual(expectedCommitResult);
		expect(dispatchResult).toStrictEqual(expectedDispatchResult);
		reset();
	});

	test('should check responseLogout-actions if response is fail', () => {
		const response = {success: false};
		const expectedCommitResult = {command: AUTH_LOGOUT_ERROR};
		const expectedDispatchResult = {
			command: USER_PROFILE_RESET,
			data: response
		};
		const router = new Router();

		responseLogout({commit, dispatch, router}, response);
		expect(commitResult).toStrictEqual(expectedCommitResult);
		expect(dispatchResult).toStrictEqual(expectedDispatchResult);
		expect(router.path).toBe(PATHS.login);
		reset();
	});

	test('should check responseLogout-actions', () => {
		const response = {success: true};
		const expectedCommitResult = {command: AUTH_LOGOUT_SUCCESS, data: response};
		const expectedDispatchResult = {
			command: USER_PROFILE_RESET,
			data: response
		};
		const router = new Router();

		responseLogout({commit, dispatch, router}, response);
		expect(commitResult).toStrictEqual(expectedCommitResult);
		expect(dispatchResult).toStrictEqual(expectedDispatchResult);
		expect(router.path).toBe(PATHS.login);
		reset();
	});
});
