import { TAG } from "../../sconst/tag";
import config from "../../../config";
import { AUTH } from "../../sconst/auth";
import { CONNECTION } from "../../sconst/connection";
import { I18N } from "../../sconst/i18n";
import { NOTIFICATION } from "../../sconst/notification";

const processClientParamsSubscription = ({dispatch}, response) => {
	const clientParams = JSON.parse(response.body);
	dispatch(I18N.SET.LOCALE, clientParams.locale);
	dispatch(CONNECTION.SEND, {
		destination: config.requests.i18n,
		headers: {},
		body: {}
	});
};

const processI18nSubscription = ({dispatch}, response) => {
	const data = JSON.parse(response.body);
	dispatch(I18N.SET.TEMPLATES, data);
};

const processAuthRequestSubscription = ({dispatch}, response) => {
	const data = JSON.parse(response.body);
	dispatch(AUTH.LOGIN.RESPONSE, data);
};

const processLogoutRequestSubscription = ({dispatch}, response) => {
	const data = JSON.parse(response.body);
	dispatch(AUTH.LOGOUT.RESPONSE, data);
};

const processTagCreationSubscription = ({dispatch}, response) => {
	const data = JSON.parse(response.body);
	if (data.success){
		dispatch(TAG.CREATED, data.value);
	} else {
		dispatch(NOTIFICATION.ERROR, data.seed);
	}
};

export {
	processClientParamsSubscription,
	processI18nSubscription,
	processAuthRequestSubscription,
	processLogoutRequestSubscription,
	processTagCreationSubscription
};
