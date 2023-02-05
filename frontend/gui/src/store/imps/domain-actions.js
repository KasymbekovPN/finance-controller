import config from "../../../config";
import { CONNECTION } from "../../sconst/connection";

const actOnTagCreation = ({dispatch}, {name}) => {
	//<
	console.log(`actOnTagCreation : name = '${name}'`);
	//<
	dispatch(CONNECTION.SEND, {
		destination: config.requests.tag.create,
		headers: {},
		body: {name}
	});
};

const actOnTagUpdating = ({dispatch}, {id, name}) => {
	dispatch(CONNECTION.SEND, {
		destination: config.requests.tag.update,
		headers: {},
		body: {id, name}
	});
};

const actOnTagDeleting = ({dispatch}, {id})  => {
	dispatch(CONNECTION.SEND, {
		destination: config.requests.tag.delete,
		headers: {},
		body: {id}
	});
};

export {
	actOnTagCreation,
	actOnTagUpdating,
	actOnTagDeleting
};
