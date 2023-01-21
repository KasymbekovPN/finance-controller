import { resetUserProfile, setUserProfile } from "../../../src/store/imps/userProfile-actions";
import { USER_PROFILE_RESET, USER_PROFILE_SET } from "../../../src/store/sconst/userProfile";

describe('userProfile-actions.js', () => {
	let commitResult;
	const commit = (command, data) => {
		commitResult = data !== undefined ? { command, data } : { command };
	};
	const reset = () => { commitResult = undefined; };

	test('should check setUserProfile-actions', () => {
		const data = {value: 'value'};
		const expectedResult = {command: USER_PROFILE_SET, data};

		setUserProfile({commit}, data);
		expect(commitResult).toStrictEqual(expectedResult);
		reset();
	});

	test('should check resetUserProfile-actions', () => {
		const expectedResult = {command: USER_PROFILE_RESET};

		resetUserProfile({commit});
		expect(commitResult).toStrictEqual(expectedResult);
		reset();
	});
});
