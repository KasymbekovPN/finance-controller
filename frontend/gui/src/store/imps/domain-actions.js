import config from "../../../config";
import { CONNECTION } from "../../sconst/connection";

const actOnTagCreation = ({dispatch}, {username}) => {
	dispatch(CONNECTION.SEND, {
		destination: config.requests.tag.create,
		headers: {},
		body: {username}
	});
};

const actOnTagUpdating = ({dispatch}, {id, username}) => {
	dispatch(CONNECTION.SEND, {
		destination: config.requests.tag.update,
		headers: {},
		body: {id, username}
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
