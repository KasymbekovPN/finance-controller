import {
	mutateOnTagAddModelHide,
	mutateOnTagAddModelShow
} from "../../../src/store/imps/signal-mutations";

describe('signal-mutations.js', () => {

	test('should check mutateOnTagAddModelShow mutation', () => {
		const expectedState = {tagAddModalVisible: true};

		let state = {};
		mutateOnTagAddModelShow(state);
		expect(state).toStrictEqual(expectedState);
	});

	test('should check mutateOnTagAddModelHide mutation', () => {
		const expectedState = {tagAddModalVisible: false};

		let state = {};
		mutateOnTagAddModelHide(state);
		expect(state).toStrictEqual(expectedState);
	});
});
