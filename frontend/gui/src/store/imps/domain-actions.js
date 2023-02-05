import config from "../../../config";
import { CONNECTION } from "../../sconst/connection";

const processTagCreationRequest = ({dispatch}, {name}) => {
	//<
	console.log(`processTagCreationRequest : name = '${name}'`);
	//<
	dispatch(CONNECTION.SEND, {
		destination: config.requests.tag.create,
		headers: {},
		body: {name}
	});
};

const processTagUpdatingRequest = ({dispatch}, {id, name}) => {
	dispatch(CONNECTION.SEND, {
		destination: config.requests.tag.update,
		headers: {},
		body: {id, name}
	});
};

const processTagDeletingRequest = ({dispatch}, {id})  => {
	dispatch(CONNECTION.SEND, {
		destination: config.requests.tag.delete,
		headers: {},
		body: {id}
	});
};

export {
	processTagCreationRequest,
	processTagUpdatingRequest,
	processTagDeletingRequest
};
