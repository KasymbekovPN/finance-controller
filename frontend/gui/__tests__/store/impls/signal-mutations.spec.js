import config from "../../../config";
import {
	mutateOnSomeModalShow,
	mutateOnSomeModalHide
} from "../../../src/store/imps/signal-mutations";

describe('signal-mutations.js', () => {

	const invalidRoute = 'invalid.route';

	test('should check mutateOnSomeModalShow if roure == config.paths.actions', () => {
		const expectedState = {actionModalVisible: true};

		let state = {};
		mutateOnSomeModalShow(state, config.paths.actions);
		expect(state).toStrictEqual(expectedState);
	});

	test('should check mutateOnSomeModalShow if roure == config.paths.payments', () => {
		const expectedState = {paymentModalVisible: true};

		let state = {};
		mutateOnSomeModalShow(state, config.paths.payments);
		expect(state).toStrictEqual(expectedState);
	});

	test('should check mutateOnSomeModalShow if roure == config.paths.sellers', () => {
		const expectedState = {sellerModalVisible: true};

		let state = {};
		mutateOnSomeModalShow(state, config.paths.sellers);
		expect(state).toStrictEqual(expectedState);
	});

	test('should check mutateOnSomeModalShow if roure == config.paths.addresses', () => {
		const expectedState = {addressModalVisisble: true};

		let state = {};
		mutateOnSomeModalShow(state, config.paths.addresses);
		expect(state).toStrictEqual(expectedState);
	});

	test('should check mutateOnSomeModalShow if roure == config.paths.streets', () => {
		const expectedState = {streetModalVisible: true};

		let state = {};
		mutateOnSomeModalShow(state, config.paths.streets);
		expect(state).toStrictEqual(expectedState);
	});

	test('should check mutateOnSomeModalShow if roure == config.paths.cities', () => {
		const expectedState = {cityModalVisible: true};

		let state = {};
		mutateOnSomeModalShow(state, config.paths.cities);
		expect(state).toStrictEqual(expectedState);
	});

	test('should check mutateOnSomeModalShow if roure == config.paths.regions', () => {
		const expectedState = {regionModalVisible: true};

		let state = {};
		mutateOnSomeModalShow(state, config.paths.regions);
		expect(state).toStrictEqual(expectedState);
	});

	test('should check mutateOnSomeModalShow if roure == config.paths.countries', () => {
		const expectedState = {countryModalVisisble: true};

		let state = {};
		mutateOnSomeModalShow(state, config.paths.countries);
		expect(state).toStrictEqual(expectedState);
	});

	test('should check mutateOnSomeModalShow if roure == config.paths.products', () => {
		const expectedState = {productModalVisible: true};

		let state = {};
		mutateOnSomeModalShow(state, config.paths.products);
		expect(state).toStrictEqual(expectedState);
	});

	test('should check mutateOnSomeModalShow if roure == config.paths.tags', () => {
		const expectedState = {tagModalVisible: true};

		let state = {};
		mutateOnSomeModalShow(state, config.paths.tags);
		expect(state).toStrictEqual(expectedState);
	});

	test('should check mutateOnSomeModalShow if roure has unknown value', () => {
		const expectedState = {};

		let state = {};
		mutateOnSomeModalShow(state, invalidRoute);
		expect(state).toStrictEqual(expectedState);
	});

	test('should check mutateOnSomeModalHide if roure == config.paths.actions', () => {
		const expectedState = {actionModalVisible: false};

		let state = {};
		mutateOnSomeModalHide(state, config.paths.actions);
		expect(state).toStrictEqual(expectedState);
	});

	test('should check mutateOnSomeModalHide if roure == config.paths.payments', () => {
		const expectedState = {paymentModalVisible: false};

		let state = {};
		mutateOnSomeModalHide(state, config.paths.payments);
		expect(state).toStrictEqual(expectedState);
	});

	test('should check mutateOnSomeModalHide if roure == config.paths.sellers', () => {
		const expectedState = {sellerModalVisible: false};

		let state = {};
		mutateOnSomeModalHide(state, config.paths.sellers);
		expect(state).toStrictEqual(expectedState);
	});

	test('should check mutateOnSomeModalHide if roure == config.paths.addresses', () => {
		const expectedState = {addressModalVisisble: false};

		let state = {};
		mutateOnSomeModalHide(state, config.paths.addresses);
		expect(state).toStrictEqual(expectedState);
	});

	test('should check mutateOnSomeModalHide if roure == config.paths.streets', () => {
		const expectedState = {streetModalVisible: false};

		let state = {};
		mutateOnSomeModalHide(state, config.paths.streets);
		expect(state).toStrictEqual(expectedState);
	});

	test('should check mutateOnSomeModalHide if roure == config.paths.cities', () => {
		const expectedState = {cityModalVisible: false};

		let state = {};
		mutateOnSomeModalHide(state, config.paths.cities);
		expect(state).toStrictEqual(expectedState);
	});

	test('should check mutateOnSomeModalHide if roure == config.paths.regions', () => {
		const expectedState = {regionModalVisible: false};

		let state = {};
		mutateOnSomeModalHide(state, config.paths.regions);
		expect(state).toStrictEqual(expectedState);
	});

	test('should check mutateOnSomeModalHide if roure == config.paths.countries', () => {
		const expectedState = {countryModalVisisble: false};

		let state = {};
		mutateOnSomeModalHide(state, config.paths.countries);
		expect(state).toStrictEqual(expectedState);
	});

	test('should check mutateOnSomeModalHide if roure == config.paths.products', () => {
		const expectedState = {productModalVisible: false};

		let state = {};
		mutateOnSomeModalHide(state, config.paths.products);
		expect(state).toStrictEqual(expectedState);
	});

	test('should check mutateOnSomeModalHide if roure == config.paths.tags', () => {
		const expectedState = {tagModalVisible: false};

		let state = {};
		mutateOnSomeModalHide(state, config.paths.tags);
		expect(state).toStrictEqual(expectedState);
	});

	test('should check mutateOnSomeModalHide if roure has unknown value', () => {
		const expectedState = {};

		let state = {};
		mutateOnSomeModalHide(state, invalidRoute);
		expect(state).toStrictEqual(expectedState);
	});
});
