import { DESTINATIONS } from "../../sconst/destinations";
import { AUTH_LOGIN_RESPONSE, AUTH_LOGOUT_RESPONSE } from "../sconst/auth";
import { CONNECTION_SEND } from "../sconst/connection";
import { I18N_SET_LOCALE, I18N_SET_TEMPLATES } from "../sconst/i18n";

const processClientParamsSubscription = ({dispatch}, response) => {
	const clientParams = JSON.parse(response.body);
	dispatch(I18N_SET_LOCALE, clientParams.locale);
	dispatch(CONNECTION_SEND, {
		destination: DESTINATIONS.i18n,
		headers: {},
		body: {}
	});
};

const processI18nSubscription = ({dispatch}, response) => {
	const data = JSON.parse(response.body);
	dispatch(I18N_SET_TEMPLATES, data);
};

const processAuthRequestSubscription = ({dispatch}, response) => {
	const data = JSON.parse(response.body);
	dispatch(AUTH_LOGIN_RESPONSE, data);
};

const processLogoutRequestSubscription = ({dispatch}, response) => {
	const data = JSON.parse(response.body);
	dispatch(AUTH_LOGOUT_RESPONSE, data);
};

export {
	processClientParamsSubscription,
	processI18nSubscription,
	processAuthRequestSubscription,
	processLogoutRequestSubscription
};
