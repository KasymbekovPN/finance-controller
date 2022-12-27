import { CONNECTION_SEND } from "@/store/actions/connection";

//< test
function loadParams(store){
	store.dispatch(CONNECTION_SEND, {
		destination: '/clientParamsRequest',
		headers: {},
		body: {}
	});
}

export {
	loadParams
};
