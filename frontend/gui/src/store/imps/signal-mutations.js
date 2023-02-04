import config from "../../../config";

const showMutations = {
	[config.paths.actions]: state => { state.actionModalVisible = true; },
	[config.paths.payments]: state => { state.paymentModalVisible = true; },
	[config.paths.sellers]: state => { state.sellerModalVisible = true; },
	[config.paths.addresses]: state => { state.addressModalVisisble = true; },
	[config.paths.streets]: state => { state.streetModalVisible = true; },
	[config.paths.cities]: state => { state.cityModalVisible = true; },
	[config.paths.regions]: state => { state.regionModalVisible = true; },
	[config.paths.countries]: state => { state.countryModalVisisble = true; },
	[config.paths.products]: state => { state.productModalVisible = true },
	[config.paths.tags]: state => { state.tagModalVisible = true }
};

const hideMutations = {
	[config.paths.actions]: state => { state.actionModalVisible = false; },
	[config.paths.payments]: state => { state.paymentModalVisible = false; },
	[config.paths.sellers]: state => { state.sellerModalVisible = false; },
	[config.paths.addresses]: state => { state.addressModalVisisble = false; },
	[config.paths.streets]: state => { state.streetModalVisible = false; },
	[config.paths.cities]: state => { state.cityModalVisible = false; },
	[config.paths.regions]: state => { state.regionModalVisible = false; },
	[config.paths.countries]: state => { state.countryModalVisisble = false; },
	[config.paths.products]: state => { state.productModalVisible = false },
	[config.paths.tags]: state => { state.tagModalVisible = false }
};

const mutateOnSomeModalShow = (state, route) => {
	if (showMutations[route] !== undefined){
		showMutations[route](state);
	}
};

const mutateOnSomeModalHide = (state, route) => {
	if (hideMutations[route] !== undefined){
		hideMutations[route](state);
	}
};

export {
	mutateOnSomeModalShow,
	mutateOnSomeModalHide
};
