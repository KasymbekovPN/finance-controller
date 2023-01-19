import {
	CONNECTION_CONNECT,
	CONNECTION_CREATE,
	CONNECTION_DISCONNECT,
	CONNECTION_SEND
} from "../sconst/connection";

const actOnConnectionCreation = (commit, dispatch, connection, sessionId) => {
	commit(CONNECTION_CREATE, {connection, sessionId});
	dispatch(CONNECTION_CONNECT);
};

const doOnConnection = ({commit}) => {
	commit(CONNECTION_CONNECT);
};

const doOnDisconnection = ({commit}) => {
	commit(CONNECTION_DISCONNECT);
};

const doOnSending = ({commit}, {destination, headers, body}) => {
	commit(CONNECTION_SEND, {destination, headers, body});
};

export {
	actOnConnectionCreation,
	doOnConnection,
	doOnDisconnection,
	doOnSending
};
