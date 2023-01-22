import {
	requestLogin,
	requestLogout,
	responseLogin,
	responseLogout
} from "../../../src/store/imps/auth-actions";
import { DESTINATIONS } from "../../../src/sconst/destinations";
import { AUTH } from "../../../src/sconst/auth";
import { CONNECTION } from "../../../src/sconst/connection";
import { USER } from "../../../src/sconst/userProfile";
import { PATHS } from "../../../src/sconst/paths";

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
		const expectedCommitResult = {command: AUTH.LOGIN.REQUEST};
		const expectedDispatchResult = {
			command: CONNECTION.SEND,
			data: {
				destination: DESTINATIONS.AUTH,
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
		const expectedCommitResult = {command: AUTH.LOGIN.ERROR};
		const expectedDispatchResult = {
			command: USER.PROFILE.SET,
			data: response
		};
		const router = new Router();

		responseLogin({commit, dispatch, router}, response);
		expect(commitResult).toStrictEqual(expectedCommitResult);
		expect(dispatchResult).toStrictEqual(expectedDispatchResult);
		expect(router.path).toBe(PATHS.HOME);
		reset();
	});

	test('should check responseLogin-actions', () => {
		const response = {success: true};
		const expectedCommitResult = {command: AUTH.LOGIN.SUCCESS, data: response};
		const expectedDispatchResult = {
			command: USER.PROFILE.SET,
			data: response
		};
		const router = new Router();

		responseLogin({commit, dispatch, router}, response);
		expect(commitResult).toStrictEqual(expectedCommitResult);
		expect(dispatchResult).toStrictEqual(expectedDispatchResult);
		expect(router.path).toBe(PATHS.HOME);
		reset();
	});


	test('should check requestLogout-actions', () => {
		const expectedCommitResult = {command: AUTH.LOGOUT.REQUEST};
		const expectedDispatchResult = {
			command: CONNECTION.SEND,
			data: {
				destination: DESTINATIONS.LOGOUT,
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
		const expectedCommitResult = {command: AUTH.LOGOUT.ERROR};
		const expectedDispatchResult = {
			command: USER.PROFILE.RESET,
			data: response
		};
		const router = new Router();

		responseLogout({commit, dispatch, router}, response);
		expect(commitResult).toStrictEqual(expectedCommitResult);
		expect(dispatchResult).toStrictEqual(expectedDispatchResult);
		expect(router.path).toBe(PATHS.LOGIN);
		reset();
	});

	test('should check responseLogout-actions', () => {
		const response = {success: true};
		const expectedCommitResult = {command: AUTH.LOGOUT.SUCCESS, data: response};
		const expectedDispatchResult = {
			command: USER.PROFILE.RESET,
			data: response
		};
		const router = new Router();

		responseLogout({commit, dispatch, router}, response);
		expect(commitResult).toStrictEqual(expectedCommitResult);
		expect(dispatchResult).toStrictEqual(expectedDispatchResult);
		expect(router.path).toBe(PATHS.LOGIN);
		reset();
	});
});
