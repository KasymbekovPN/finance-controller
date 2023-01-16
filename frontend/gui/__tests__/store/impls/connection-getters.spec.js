import { isConnected } from "../../../src/store/imps/connection-getters";

describe('connection-getters.js', () => {

	test('should check isConnected-getter if state.connection is undefined', () => {
		let state = {};
		expect(isConnected(state)).toBe(false);
	});

	test('should check isConnected-getter if state.connection.connected is undefined', () => {
		let state = {
			connection: {}
		};
		expect(isConnected(state)).toBe(false);
	});

	test('should check isConnected-getter if state.connection.connected is false', () => {
		let state = {
			connection: {connected: false}
		};
		expect(isConnected(state)).toBe(false);
	});

	test('should check isConnected-getter if state.connection.connected is true', () => {
		let state = {
			connection: {connected: true}
		};
		expect(isConnected(state)).toBe(true);
	});
});
