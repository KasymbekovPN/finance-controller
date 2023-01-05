import { CLIENT_PARAMS_SET } from "../actions/clientParams";
import { CONNECTION_SEND } from "../actions/connection";
import { I18N_INIT } from "../actions/i18n";
import { SUBSCRIPTION_CLIENT_PARAMS, SUBSCRIPTION_I18N } from "../actions/subscription";

const state = {};

const getters = {};

const actions = {
	[SUBSCRIPTION_CLIENT_PARAMS]: ({dispatch}, response) => {
		const clientParams = JSON.parse(response.body);
		dispatch(CLIENT_PARAMS_SET, {clientParams});
		dispatch(CONNECTION_SEND, {
			destination: '/i18nRequest',
			headers: {},
			body: {}
		});
	},
	[SUBSCRIPTION_I18N]: ({dispatch}, response) => {
		const data = JSON.parse(response.body);
		dispatch(I18N_INIT, {data});
	}
};

const mutations = {};

export default {
	state,
	getters,
	actions,
	mutations
};
