import { CONNECTION } from "../../sconst/connection";

const actOnConnectionCreation = (commit, dispatch, connection, sessionId) => {
	commit(CONNECTION.CREATE, {connection, sessionId});
	dispatch(CONNECTION.CONNECT);
};

const doOnConnection = ({commit}) => {
	commit(CONNECTION.CONNECT);
};

const doOnDisconnection = ({commit}) => {
	commit(CONNECTION.DISCONNECT);
};

const doOnSending = ({commit}, {destination, headers, body}) => {
	commit(CONNECTION.SEND, {destination, headers, body});
};

export {
	actOnConnectionCreation,
	doOnConnection,
	doOnDisconnection,
	doOnSending
};
