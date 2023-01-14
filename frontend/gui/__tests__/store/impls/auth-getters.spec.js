import { isAuthenticated, getStatus } from "../../../src/store/imps/auth-getters";

describe('auth-getters.js', () => {

	test('should check isAuthenticated-getter', () => {
		const state = {authenticated: true};
		expect(isAuthenticated(state)).toBe(true);
	});

	test('should check authStatus-getter', () => {
		const authStatus = 'authStatus';
		const state = {authStatus};
		expect(getStatus(state)).toBe(authStatus);
	});
});
