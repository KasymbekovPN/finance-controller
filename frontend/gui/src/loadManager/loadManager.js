import { CONNECTION_SEND } from "../store/actions/connection";

function loadParams(store){
	store.dispatch(CONNECTION_SEND, {
		destination: '/clientParamsRequest',
		headers: {},
		body: {}
	});
}

function loadI18n(store){
	store.dispatch(CONNECTION_SEND, {
		destination: '/i18nRequest',
		headers: {},
		body: {}
	});
}

export {
	loadParams,
	loadI18n
};
