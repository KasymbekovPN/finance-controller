import { getUserName } from "../../../src/store/imps/userProfile-getters";

describe('userProfile-getters.js', () => {

	test('should check getUsername-getters', () => {
		const username = 'username';
		const state = {username};

		expect(getUserName(state)).toBe(username);
	});
});

