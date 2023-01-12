import { Connection } from "../../connection/connection";
import {
	CONNECTION_CREATE,
	CONNECTION_CONNECT,
	CONNECTION_DISCONNECT,
	CONNECTION_SEND
} from "../sconst/connection";
import { SUBSCRIPTIONS } from "../sconst/subscription";

const state = {
	connection: undefined,
	sessionId: undefined
};

const getters = {
	isConnected: state => {
		return state.connection !== undefined && state.connection.connected;
	}
};

const actions = {
	[CONNECTION_CREATE]: ({commit, dispatch}, {clientCreator, connectionHeaders, sessionId}) => {
		const connection = new Connection(clientCreator, connectionHeaders);
		connection.openCallback = () => {
			dispatch(CONNECTION_SEND, {
				destination: '/clientParamsRequest',
				headers: {},
				body: {}
			})
		};

		for (const action in SUBSCRIPTIONS){
			connection.addSubscription(`${SUBSCRIPTIONS[action]}${sessionId}`, response => {
				dispatch(action, response);
			});
		}
		commit(CONNECTION_CREATE, {connection, sessionId});
		dispatch(CONNECTION_CONNECT);
	},
	[CONNECTION_CONNECT]: ({commit}) => {
		commit(CONNECTION_CONNECT);
	},
	[CONNECTION_DISCONNECT]: ({commit}) => {
		commit(CONNECTION_DISCONNECT);
	},
	[CONNECTION_SEND]: ({commit}, {destination, headers, body}) => {
		commit(CONNECTION_SEND, {destination, headers, body});
	}
};

const mutations = {
	[CONNECTION_CREATE]: (state, {connection, sessionId}) => {
		state.connection = connection;
		state.sessionId = sessionId;
	},
	[CONNECTION_CONNECT]: state => {
		state.connection.connect();
	},
	[CONNECTION_DISCONNECT]: state => {
		state.connection.disconnect();
	},
	[CONNECTION_SEND]: (state, {destination, headers, body}) => {
		state.connection.send(
			`/app${destination}/${state.sessionId}`,
			headers,
			JSON.stringify(body)
		);
	}
};

export default {
	state,
	getters,
	actions,
	mutations
};
