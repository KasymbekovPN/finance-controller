import config from "../../../config";
import {
	isActionsRoute,
	isAddressesRoute,
	isCitiesRoute,
	isCountriesRoute,
	isPaymentsRoute,
	isProductsRoute,
	isRegionsRoute,
	isSellersRoute,
	isStreetsRoute,
	isTagsRoute
} from "../../../src/store/imps/route-getters";

describe('route-getters.js', () => {

	test('should check isTagsRoute-getter if it is fail', () => {
		const state = {};
		expect(isTagsRoute(state)).toBe(false);
	});

	test('should check isTagsRoute-getter if it is success', () => {
		const state = {route: config.paths.tags};
		expect(isTagsRoute(state)).toBe(true);
	});

	test('should check isProductsRoute-getter if it is fail', () => {
		const state = {};
		expect(isProductsRoute(state)).toBe(false);
	});

	test('should check isProductsRoute-getter if it is success', () => {
		const state = {route: config.paths.products};
		expect(isProductsRoute(state)).toBe(true);
	});

	test('should check isCountriesRoute-getter if it is fail', () => {
		const state = {};
		expect(isCountriesRoute(state)).toBe(false);
	});

	test('should check isCountriesRoute-getter if it is success', () => {
		const state = {route: config.paths.countries};
		expect(isCountriesRoute(state)).toBe(true);
	});

	test('should check isRegionsRoute-getter if it is fail', () => {
		const state = {};
		expect(isRegionsRoute(state)).toBe(false);
	});

	test('should check isRegionsRoute-getter if it is success', () => {
		const state = {route: config.paths.regions};
		expect(isRegionsRoute(state)).toBe(true);
	});

	test('should check isCitiesRoute-getter if it is fail', () => {
		const state = {};
		expect(isCitiesRoute(state)).toBe(false);
	});

	test('should check isCitiesRoute-getter if it is success', () => {
		const state = {route: config.paths.cities};
		expect(isCitiesRoute(state)).toBe(true);
	});

	test('should check isStreetsRoute-getter if it is fail', () => {
		const state = {};
		expect(isStreetsRoute(state)).toBe(false);
	});

	test('should check isStreetsRoute-getter if it is success', () => {
		const state = {route: config.paths.streets};
		expect(isStreetsRoute(state)).toBe(true);
	});

	test('should check isAddressesRoute-getter if it is fail', () => {
		const state = {};
		expect(isAddressesRoute(state)).toBe(false);
	});

	test('should check isAddressesRoute-getter if it is success', () => {
		const state = {route: config.paths.addresses};
		expect(isAddressesRoute(state)).toBe(true);
	});

	test('should check isSellersRoute-getter if it is fail', () => {
		const state = {};
		expect(isSellersRoute(state)).toBe(false);
	});

	test('should check isSellersRoute-getter if it is success', () => {
		const state = {route: config.paths.sellers};
		expect(isSellersRoute(state)).toBe(true);
	});

	test('should check isPaymentsRoute-getter if it is fail', () => {
		const state = {};
		expect(isPaymentsRoute(state)).toBe(false);
	});

	test('should check isPaymentsRoute-getter if it is success', () => {
		const state = {route: config.paths.payments};
		expect(isPaymentsRoute(state)).toBe(true);
	});

	test('should check isActionsRoute-getter if it is fail', () => {
		const state = {};
		expect(isActionsRoute(state)).toBe(false);
	});

	test('should check isActionsRoute-getter if it is success', () => {
		const state = {route: config.paths.actions};
		expect(isActionsRoute(state)).toBe(true);
	});
});
