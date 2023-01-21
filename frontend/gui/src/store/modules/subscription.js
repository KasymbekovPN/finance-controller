import {
	processAuthRequestSubscription,
	processClientParamsSubscription,
	processI18nSubscription,
	processLogoutRequestSubscription
} from "../imps/subscription-actions";
import {
	SUBSCRIPTION_CLIENT_PARAMS,
	SUBSCRIPTION_I18N,
	SUBSCRIPTION_AUTH_REQUEST,
	SUBSCRIPTION_LOGOUT_REQUEST
} from "../sconst/subscription";

const state = {};

const getters = {};

const actions = {
	[SUBSCRIPTION_CLIENT_PARAMS]: processClientParamsSubscription,
	[SUBSCRIPTION_I18N]: processI18nSubscription,
	[SUBSCRIPTION_AUTH_REQUEST]: processAuthRequestSubscription,
	[SUBSCRIPTION_LOGOUT_REQUEST]: processLogoutRequestSubscription
};

const mutations = {};

export default {
	state,
	getters,
	actions,
	mutations
};
