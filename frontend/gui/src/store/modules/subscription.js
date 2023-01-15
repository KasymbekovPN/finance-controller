import {
	processAuthRequestSubscription,
	processClientParamsSubscription,
	processI18nSubscription
} from "../imps/subscription-actions";
import {
	SUBSCRIPTION_CLIENT_PARAMS,
	SUBSCRIPTION_I18N,
	SUBSCRIPTION_AUTH_REQUEST
} from "../sconst/subscription";

const state = {};

const getters = {};

const actions = {
	[SUBSCRIPTION_CLIENT_PARAMS]: processClientParamsSubscription,
	[SUBSCRIPTION_I18N]: processI18nSubscription,
	[SUBSCRIPTION_AUTH_REQUEST]: processAuthRequestSubscription
};

const mutations = {};

export default {
	state,
	getters,
	actions,
	mutations
};
