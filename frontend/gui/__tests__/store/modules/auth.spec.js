/**
 * @jest-environment jsdom
 */

import { CONNECTION_SEND } from '../../../src/store/actions/connection';
import { AUTH_LOGIN_REQUEST } from '../../../src/store/actions/auth';
import auth from '../../../src/store/modules/auth';

describe('auth.js', () => {
	test('should check action AUTH_LOGIN_REQUEST', () => {
		const { actions } = auth;

		let result;
		const dispatch = (command, {destination, headers, body}) => {
			result = {
				command,
				destination,
				headers,
				body
			};
		};

		const user = {data: 'data'};
		actions[AUTH_LOGIN_REQUEST]({dispatch}, {user});

		const expectedResult = {
			command: CONNECTION_SEND,
			destination: '/authRequest',
			headers: {},
			body: user
		};

		expect(result).toStrictEqual(expectedResult);
	});
});
