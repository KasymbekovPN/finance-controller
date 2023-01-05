import { CONNECTION_SEND } from "../store/actions/connection";

//< del
function loadParams(store){
	store.dispatch(CONNECTION_SEND, {
		destination: '/clientParamsRequest',
		headers: {},
		body: {}
	});
}

//< del
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
