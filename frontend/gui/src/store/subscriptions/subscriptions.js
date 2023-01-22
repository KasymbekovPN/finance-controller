import { SUBSCRIPTION } from "../../sconst/subscription";

export const SUBSCRIPTIONS = {
	[SUBSCRIPTION.CLIENT_PARAMS]: '/topic/clientParamsResponse/',
	[SUBSCRIPTION.I18N]: '/topic/i18nResponse/',
	[SUBSCRIPTION.AUTH_REQUEST]: '/topic/authResponse/',
	[SUBSCRIPTION.LOGOUT_REQUEST]: '/topic/logoutResponse/'
};
