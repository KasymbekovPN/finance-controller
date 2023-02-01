import { SIGNAL } from "../../../src/sconst/signal";
import {
	actOnTagAddModelHide,
	actOnTagAddModelShow
} from "../../../src/store/imps/signal-actions";

describe('signal-actions.js', () => {

	let commitResult;
	const commit = (command, data) => {
		commitResult = data !== undefined ? { command, data } : { command };
	};

	const reset = () => {
		commitResult = undefined;
	};

	test('should check actOnTagAddModelShow-actions', () => {
		const expectedResult = {command: SIGNAL.MODAL.TAG.ADD.SHOW};
		actOnTagAddModelShow({commit});

		expect(commitResult).toStrictEqual(expectedResult);
		reset();
	});

	test('should check actOnTagAddModelHide-actions', () => {
		const expectedResult = {command: SIGNAL.MODAL.TAG.ADD.HIDE};
		actOnTagAddModelHide({commit});

		expect(commitResult).toStrictEqual(expectedResult);
		reset();
	});
});
