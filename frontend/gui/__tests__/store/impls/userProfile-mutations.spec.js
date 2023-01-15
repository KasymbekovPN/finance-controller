import { mutateOnSetUserProfile } from "../../../src/store/imps/userProfile-mutations";

describe('userProfile-mutations.js', () => {

	test('should check mutateOnSetUserProfile', () => {
		const username = 'username';
		const expectedState = {username};
		const data = {username};

		let state = {};
		mutateOnSetUserProfile(state, data);
		expect(state).toStrictEqual(expectedState);
	});
});
