import { SIGNAL } from "../../../src/sconst/signal";
import {
	actOnSomeModalHide,
	actOnSomeModalShow
} from "../../../src/store/imps/signal-actions";

describe('signal-actions.js', () => {

	const route = 'some.route';

	let commitResult;
	const commit = (command, data) => {
		commitResult = data !== undefined ? { command, data } : { command };
	};

	const reset = () => {
		commitResult = undefined;
	};

	test('should check actOnSomeModalShow action', () => {
		const expectedCommitResult = {command: SIGNAL.MODAL.SOME.ADD.SHOW, data: route};

		actOnSomeModalShow({commit}, route);
		expect(commitResult).toStrictEqual(expectedCommitResult);
		reset();
	});

	test('should check actOnSomeModalHide action', () => {
		const expectedCommitResult = {command: SIGNAL.MODAL.SOME.ADD.HIDE, data: route};

		actOnSomeModalHide({commit}, route);
		expect(commitResult).toStrictEqual(expectedCommitResult);
		reset();
	});
});
