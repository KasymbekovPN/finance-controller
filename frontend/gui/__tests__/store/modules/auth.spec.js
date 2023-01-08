/**
 * @jest-environment jsdom
 */

import { CONNECTION_SEND } from '../../../src/store/actions/connection';
import { AUTH_LOGIN_ERROR, AUTH_LOGIN_REQUEST, AUTH_LOGIN_RESPONSE, AUTH_LOGIN_SUCCESS } from '../../../src/store/actions/auth';
import { USER_PROFILE_SET } from '../../../src/store/actions/userProfile';
import auth from '../../../src/store/modules/auth';
import { AUTH_STATUS_ERROR, AUTH_STATUS_LOADING, AUTH_STATUS_SUCCESS } from '../../../src/store/status/auth';

describe('auth.js', () => {

	test('should check action AUTH_LOGIN_REQUEST', () => {
		const { actions } = auth;

		const user = {data: 'user'};
		const expectedCommitResult = {command: AUTH_LOGIN_REQUEST};
		const expectedDispatchResult = {
			command: CONNECTION_SEND,
			data: {
				destination: '/authRequest',
				headers: {},
				body: user
			}
		};

		let commitResult;
		const commit = (command, data) => {
			commitResult = data !== undefined ? { command, data } : { command };
		};
		let dispatchResult;
		const dispatch = (command, data) => {
			dispatchResult = data !== undefined ? { command, data } : { command };
		};

		actions[AUTH_LOGIN_REQUEST]({commit, dispatch}, user);

		expect(commitResult).toStrictEqual(expectedCommitResult);
		expect(dispatchResult).toStrictEqual(expectedDispatchResult);
	});

	test('should check action AUTH_LOGIN_RESPONSE if fail', () => {
		const { actions } = auth;

		const response = {success: false};
		const expectedCommitResult = { command: AUTH_LOGIN_ERROR, data: response };
		const expectedDispatchResult = { command: USER_PROFILE_SET, data: response };

		let commitResult;
		const commit = (command, data) => {
			commitResult = data !== undefined ? { command, data } : { command };
		};
		let dispatchResult;
		const dispatch = (command, data) => {
			dispatchResult = data !== undefined ? { command, data } : { command };
		};

		actions[AUTH_LOGIN_RESPONSE]({commit, dispatch}, response);

		expect(commitResult).toStrictEqual(expectedCommitResult);
		expect(dispatchResult).toStrictEqual(expectedDispatchResult);
	});

	test('should check action AUTH_LOGIN_RESPONSE if success', () => {
		const { actions } = auth;

		const response = {success: true};
		const expectedCommitResult = { command: AUTH_LOGIN_SUCCESS, data: response };
		const expectedDispatchResult = { command: USER_PROFILE_SET, data: response };

		let commitResult;
		const commit = (command, data) => {
			commitResult = data !== undefined ? { command, data } : { command };
		};
		let dispatchResult;
		const dispatch = (command, data) => {
			dispatchResult = data !== undefined ? { command, data } : { command };
		};

		actions[AUTH_LOGIN_RESPONSE]({commit, dispatch}, response);

		expect(commitResult).toStrictEqual(expectedCommitResult);
		expect(dispatchResult).toStrictEqual(expectedDispatchResult);
	});

	test('should check mutation AUTH_LOGIN_REQUEST', () => {
		const { mutations } = auth;
		let state = {};

		mutations[AUTH_LOGIN_REQUEST](state);
		expect(state.status).toBe(AUTH_STATUS_LOADING);
	});

	test('should check mutation AUTH_LOGIN_SUCCESS', () => {
		const { mutations } = auth;
		const token = 'some.token';
		const response = { token };
		const expectedState = {
			token: token,
			status: AUTH_STATUS_SUCCESS,
			hasLoadedOnce: true
		};

		let state = {};
		mutations[AUTH_LOGIN_SUCCESS](state, response);
		expect(state).toStrictEqual(expectedState);

		const userToken = localStorage.getItem('user-token');
		localStorage.removeItem("user-token");
		expect(userToken).toBe(token);
	});

	test('should check mutation AUTH_LOGIN_ERROR', () => {
		const { mutations } = auth;
		const response = {};
		const expectedState = {
			token: '',
			status: AUTH_STATUS_ERROR,
			hasLoadedOnce: true
		};

		let state = {};
		mutations[AUTH_LOGIN_ERROR](state, response);
		expect(state).toStrictEqual(expectedState);

		const userToken = localStorage.getItem('user-token');
		localStorage.removeItem("user-token");
		expect(userToken).toBeNull();
	});
});
