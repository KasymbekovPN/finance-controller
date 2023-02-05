import {
	processAuthRequestSubscription,
	processClientParamsSubscription,
	processI18nSubscription,
	processLogoutRequestSubscription,
	processTagCreationSubscription
} from "../imps/subscription-actions";
import { SUBSCRIPTION } from "../../sconst/subscription";

const state = {};

const getters = {};

const actions = {
	[SUBSCRIPTION.CLIENT_PARAMS]: processClientParamsSubscription,
	[SUBSCRIPTION.I18N]: processI18nSubscription,
	[SUBSCRIPTION.AUTH_REQUEST]: processAuthRequestSubscription,
	[SUBSCRIPTION.LOGOUT_REQUEST]: processLogoutRequestSubscription,
	[SUBSCRIPTION.TAG_CREATION]: processTagCreationSubscription
};

const mutations = {};

export default {
	state,
	getters,
	actions,
	mutations
};
