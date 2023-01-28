import config from '../../../config';
import { Connection } from "../../connection/connection";
import { isConnected } from "../imps/connection-getters";
import { CONNECTION } from "../../sconst/connection";
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
import { SUBSCRIPTIONS } from "../subscriptions/subscriptions";
import { AUTH } from "@/sconst/auth";

const state = {
	connection: undefined,
	sessionId: undefined
};

const getters = {
	isConnected: isConnected
};

const actions = {
	[CONNECTION.CREATE]: ({commit, dispatch}) => {
		const sessionId = uuid();
		const client = Stomp.over(() => {return new WebSocket(config.webSocket.url)});
		client.reconnect_delay = config.webSocket.client.reconnectDelay;

		const connection = new Connection(client);
		connection.subscriptions = SUBSCRIPTIONS;
		connection.subscriptionCallback = action => response => {
			dispatch(action, response);
		};
		connection.openCallback = () => {
			dispatch(CONNECTION.SEND, {
				destination: config.requests.clientParams,
				headers: {},
				body: {}
			})
		};
		connection.closeCallback = () => {
			dispatch(AUTH.ON.DISCONNECTED);
		};

		actOnConnectionCreation(
			commit,
			dispatch,
			connection,
			sessionId
		);
	},
	[CONNECTION.CONNECT]: doOnConnection,
	[CONNECTION.DISCONNECT]: doOnDisconnection,
	[CONNECTION.SEND]: doOnSending
};

const mutations = {
	[CONNECTION.CREATE]: mutateOnConnectionCreation,
	[CONNECTION.CONNECT]: mutateOnConnection,
	[CONNECTION.DISCONNECT]: mutateOnDisconnection,
	[CONNECTION.SEND]: mutateOnSending
};

export default {
	state,
	getters,
	actions,
	mutations
};
