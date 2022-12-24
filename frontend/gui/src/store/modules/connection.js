import {
	CONNECTION_CREATE,
	CONNECTION_CONNECT,
	CONNECTION_DISCONNECT,
	CONNECTION_SEND
} from "../actions/connection";

const state = {connection: undefined};

const getters = {
	isConnected: state => {
		return state.connection !== undefined && state.connection.connected;
	}
};

const actions = {
	[CONNECTION_CREATE]: ({commit, dispatch}, clientCreator, connectionHeaders) => {},
	[CONNECTION_CONNECT]: ({commit, dispatch}) => {},
	[CONNECTION_DISCONNECT]: ({commit, dispatch}) => {},
	[CONNECTION_SEND]: ({commit, dispatch}) => {}
};

const mutations = {
	[CONNECTION_CREATE]: (state, connection) => {},
	[CONNECTION_CONNECT]: state => {},
	[CONNECTION_DISCONNECT]: state => {},
	[CONNECTION_SEND]: state => {}
};

export default {
	state,
	getters,
	actions,
	mutations
};
