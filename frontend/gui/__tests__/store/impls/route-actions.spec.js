import { actOnRouteChanging } from "../../../src/store/imps/route-actions";
import { ROUTE } from "../../../src/sconst/route";

describe('route-actions.js', () => {

	let commitResult;
	const commit = (command, data) => {
		commitResult = { command, data };
	};

	const reset = () => {
		commitResult = undefined;
	};

	test('should check actOnRouteChanging-actions', () => {
		const route = '/route';
		const expectedCommitResult = {command: ROUTE.ON.CHANGING, data: route};

		actOnRouteChanging({commit}, route);
		expect(commitResult).toStrictEqual(expectedCommitResult);
		reset();
	});
});
