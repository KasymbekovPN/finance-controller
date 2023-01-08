import { SUBSCRIPTION_AUTH_REQUEST } from '../../../src/store/actions/subscription';
import { AUTH_LOGIN_RESPONSE } from '../../../src/store/actions/auth';
import subscription from '../../../src/store/modules/subscription';

describe('subscription.js', () => {

	test('should check action SUBSCRIPTION_AUTH_REQUEST', () => {
		const { actions } = subscription;

		const response = {
			body: '{\"value\": \"value\"}'
		};
		const expectedResult = {
			command: AUTH_LOGIN_RESPONSE,
			data: {value: 'value'}
		};

		let result;
		const dispatch = (command, data) => {result = {command, data}};
		actions[SUBSCRIPTION_AUTH_REQUEST]({dispatch}, response);

		expect(result).toStrictEqual(expectedResult);
	});
});
