import config from "../../../config";

const isTagsRoute = state => {
	return state.route === config.paths.tags;
};

const isProductsRoute = state => {
	return state.route === config.paths.products;
};

const isCountriesRoute = state => {
	return state.route === config.paths.countries;
};

const isRegionsRoute = state => {
	return state.route === config.paths.regions;
};

const isCitiesRoute = state => {
	return state.route === config.paths.cities;
};

const isStreetsRoute = state => {
	return state.route === config.paths.streets;
};

const isAddressesRoute = state => {
	return state.route === config.paths.addresses;
};

const isSellersRoute = state => {
	return state.route === config.paths.sellers;
};

const isPaymentsRoute = state => {
	return state.route === config.paths.payments;
};

const isActionsRoute = state => {
	return state.route === config.paths.actions;
};

export {
	isTagsRoute,
	isProductsRoute,
	isCountriesRoute,
	isRegionsRoute,
	isCitiesRoute,
	isStreetsRoute,
	isAddressesRoute,
	isSellersRoute,
	isPaymentsRoute,
	isActionsRoute
};
