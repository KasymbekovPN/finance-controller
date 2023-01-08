import { USER_PROFILE_SET } from '../../../src/store/actions/userProfile';
import userProfile from '../../../src/store/modules/userProfile';

describe('userProfile.js', () => {
	test('should check getter username', () => {
		const { getters } = userProfile;

		const expectedUsername = 'username';
		const state = {username: expectedUsername};

		const username = getters['username'](state);
		expect(username).toBe(expectedUsername);
	});

	test('should check action USER_PROFILE_SET', () => {
		const { actions } = userProfile;

		const data = {username: 'username'};
		const expectedResult = {
			command: USER_PROFILE_SET,
			data: data
		};

		let result;
		const commit = (command, data) => {
			result = { command, data };
		};

		actions[USER_PROFILE_SET]({commit}, data);
		expect(result).toStrictEqual(expectedResult);
	});

	test('should check mutation USER_PROFILE_SET', () => {
		const { mutations } = userProfile;

		const username = 'username';
		const expectedState = {username};

		const data = {username};
		let state = {};
		mutations[USER_PROFILE_SET](state, data);

		expect(state).toStrictEqual(expectedState);
	});
});
