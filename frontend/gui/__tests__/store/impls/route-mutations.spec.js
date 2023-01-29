import { mutateOnRouteChanging } from "../../../src/store/imps/route-mutations";

describe('route-mutations.js', () => {

	test('should check mutateOnRouteChanging', () => {
		const route = '/route';
		const expectedState = {route};

		let state = {};
		mutateOnRouteChanging(state, route);
		expect(state).toStrictEqual(expectedState);
	});
});
