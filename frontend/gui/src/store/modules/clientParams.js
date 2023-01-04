import { CLIENT_PARAMS_SET } from "../actions/clientParams";

const state = {
	locale: 'en'
};

const getters = {
	getLocale: state => state.locale
};

const actions = {
	[CLIENT_PARAMS_SET]: ({commit}, {clientParams}) => {
		commit(CLIENT_PARAMS_SET, clientParams);
	}
};

const mutations = {
	[CLIENT_PARAMS_SET]: (state, clientParams) => {
		if (clientParams.locale !== undefined){
			state.locale = clientParams.locale;
		}
	}
};

export default {
	state,
	getters,
	actions,
	mutations
};
