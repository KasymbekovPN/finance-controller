import { isTagAddModalVisible } from "../../../src/store/imps/signal-getters";

describe('signal-getters.js', () => {

	test('should check isTagAddModalVisible-getter if it is false', () => {
		const state = {tagAddModalVisible: false};
		const result = isTagAddModalVisible(state);

		expect(result).toBe(false);
	});

	test('should check isTagAddModalVisible-getter if it is true', () => {
		const state = {tagAddModalVisible: true};
		const result = isTagAddModalVisible(state);

		expect(result).toBe(true);
	});
});
