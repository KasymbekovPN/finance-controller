import { AUTH_LOGIN_RESPONSE } from "../sconst/auth";
import { CONNECTION_SEND } from "../sconst/connection";
import { I18N_SET_LOCALE, I18N_SET_TEMPLATES } from "../sconst/i18n";
import {
	SUBSCRIPTION_CLIENT_PARAMS,
	SUBSCRIPTION_I18N,
	SUBSCRIPTION_AUTH_REQUEST
} from "../sconst/subscription";

const state = {};

const getters = {};

const actions = {
	[SUBSCRIPTION_CLIENT_PARAMS]: ({dispatch}, response) => {
		const clientParams = JSON.parse(response.body);
		dispatch(I18N_SET_LOCALE, clientParams.locale);
		dispatch(CONNECTION_SEND, {
			destination: '/i18nRequest',
			headers: {},
			body: {}
		});
	},
	[SUBSCRIPTION_I18N]: ({dispatch}, response) => {
		const data = JSON.parse(response.body);
		dispatch(I18N_SET_TEMPLATES, data);
	},
	[SUBSCRIPTION_AUTH_REQUEST]: ({dispatch}, response) => {
		const data = JSON.parse(response.body);
		dispatch(AUTH_LOGIN_RESPONSE, data);
	}
};

const mutations = {};

export default {
	state,
	getters,
	actions,
	mutations
};
