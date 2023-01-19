import { DESTINATIONS } from "../../sconst/destinations";
import { Connection } from "../../connection/connection";
import { isConnected } from "../imps/connection-getters";
import {
	CONNECTION_CREATE,
	CONNECTION_CONNECT,
	CONNECTION_DISCONNECT,
	CONNECTION_SEND
} from "../sconst/connection";
import { SUBSCRIPTIONS } from "../sconst/subscription";
import {
	doOnConnection,
	doOnDisconnection,
	doOnSending,
	actOnConnectionCreation
} from "../imps/connection-actions";
import {
	mutateOnConnectionCreation,
	mutateOnConnection,
	mutateOnDisconnection,
	mutateOnSending
} from "../imps/connection-mutations";
import { Stomp } from "@stomp/stompjs";
import { v4 as uuid } from "uuid";
import config from "../../../config";

const state = {
	connection: undefined,
	sessionId: undefined
};

const getters = {
	isConnected: isConnected
};

const actions = {
	[CONNECTION_CREATE]: ({commit, dispatch}) => {
		const sessionId = uuid();
		const client = Stomp.over(() => {return new WebSocket(config.webSocket.url)});
		client.reconnect_delay = config.webSocket.client.reconnectDelay;

		const connection = new Connection(client);
		connection.subscriptions = SUBSCRIPTIONS;
		connection.subscriptionCallback = action => response => {
			dispatch(action, response);
		};
		connection.openCallback = () => {
			dispatch(CONNECTION_SEND, {
				destination: DESTINATIONS.clientParams,
				headers: {},
				body: {}
			})
		};

		actOnConnectionCreation(
			commit,
			dispatch,
			connection,
			sessionId
		);
	},
	[CONNECTION_CONNECT]: doOnConnection,
	[CONNECTION_DISCONNECT]: doOnDisconnection,
	[CONNECTION_SEND]: doOnSending
};

const mutations = {
	[CONNECTION_CREATE]: mutateOnConnectionCreation,
	[CONNECTION_CONNECT]: mutateOnConnection,
	[CONNECTION_DISCONNECT]: mutateOnDisconnection,
	[CONNECTION_SEND]: mutateOnSending
};

export default {
	state,
	getters,
	actions,
	mutations
};
