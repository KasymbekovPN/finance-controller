import { I18N_SET } from "../actions/i18n";

const state = {
	i18n: undefined
};

const getters = {};

const actions = {
	[I18N_SET]: ({commit}, {i18n}) => {
		console.log('action');
		console.log(i18n);

		commit(I18N_SET, i18n);
	}
};

const mutations = {
	[I18N_SET]: (state, i18n) => {
		console.log('mutations');
		console.log(i18n);

		//< !!!
		// state.i18n =
	}
};

export default {
	state,
	getters,
	actions,
	mutations
};
