import {
	isActionModalVisible,
	isAddressModalVisible,
	isCityModalVisible,
	isCountryModalVisible,
	isPaymentModalVisible,
	isProductModalVisible,
	isRegionModalVisible,
	isSellerModalVisible,
	isStreetModalVisible,
	isTagModalVisible
} from "../../../src/store/imps/signal-getters";

describe('signal-getters.js', () => {

	const changeToFalse = (state, attr) => { state[attr] = false; };
	const changeToTrue = (state, attr) => { state[attr] = true; };

	test('should check action modal visible getter', () => {
		let state = {};
		expect(isActionModalVisible(state)).toBe(false);

		changeToFalse(state, 'actionModalVisible');
		expect(isActionModalVisible(state)).toBe(false);

		changeToTrue(state, 'actionModalVisible');
		expect(isActionModalVisible(state)).toBe(true);
	});

	test('should check payment modal visible getter', () => {
		let state = {};
		expect(isPaymentModalVisible(state)).toBe(false);

		changeToFalse(state, 'paymentModalVisible');
		expect(isPaymentModalVisible(state)).toBe(false);

		changeToTrue(state, 'paymentModalVisible');
		expect(isPaymentModalVisible(state)).toBe(true);
	});

	test('should check seller modal visible getter', () => {
		let state = {};
		expect(isSellerModalVisible(state)).toBe(false);

		changeToFalse(state, 'sellerModalVisible');
		expect(isSellerModalVisible(state)).toBe(false);

		changeToTrue(state, 'sellerModalVisible');
		expect(isSellerModalVisible(state)).toBe(true);
	});

	test('should check address modal visible getter', () => {
		let state = {};
		expect(isAddressModalVisible(state)).toBe(false);

		changeToFalse(state, 'addressModalVisible');
		expect(isAddressModalVisible(state)).toBe(false);

		changeToTrue(state, 'addressModalVisible');
		expect(isAddressModalVisible(state)).toBe(true);
	});

	test('should check street modal visible getter', () => {
		let state = {};
		expect(isStreetModalVisible(state)).toBe(false);

		changeToFalse(state, 'streetModalVisible');
		expect(isStreetModalVisible(state)).toBe(false);

		changeToTrue(state, 'streetModalVisible');
		expect(isStreetModalVisible(state)).toBe(true);
	});

	test('should check city modal visible getter', () => {
		let state = {};
		expect(isCityModalVisible(state)).toBe(false);

		changeToFalse(state, 'cityModalVisible');
		expect(isCityModalVisible(state)).toBe(false);

		changeToTrue(state, 'cityModalVisible');
		expect(isCityModalVisible(state)).toBe(true);
	});

	test('should check region modal visible getter', () => {
		let state = {};
		expect(isRegionModalVisible(state)).toBe(false);

		changeToFalse(state, 'regionModalVisible');
		expect(isRegionModalVisible(state)).toBe(false);

		changeToTrue(state, 'regionModalVisible');
		expect(isRegionModalVisible(state)).toBe(true);
	});

	test('should check country modal visible getter', () => {
		let state = {};
		expect(isCountryModalVisible(state)).toBe(false);

		changeToFalse(state, 'countryModalVisible');
		expect(isCountryModalVisible(state)).toBe(false);

		changeToTrue(state, 'countryModalVisible');
		expect(isCountryModalVisible(state)).toBe(true);
	});

	test('should check product modal visible getter', () => {
		let state = {};
		expect(isProductModalVisible(state)).toBe(false);

		changeToFalse(state, 'productModalVisible');
		expect(isProductModalVisible(state)).toBe(false);

		changeToTrue(state, 'productModalVisible');
		expect(isProductModalVisible(state)).toBe(true);
	});

	test('should check tag modal visible getter', () => {
		let state = {};
		expect(isTagModalVisible(state)).toBe(false);

		changeToFalse(state, 'tagModalVisible');
		expect(isTagModalVisible(state)).toBe(false);

		changeToTrue(state, 'tagModalVisible');
		expect(isTagModalVisible(state)).toBe(true);
	});
});
