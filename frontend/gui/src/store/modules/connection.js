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
	initializeAfterCreation
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

		//< FC-000181 - begin
		const connection = new Connection(client, {});
		const setOpenCallback = connection => {
			connection.openCallback = () => {
				dispatch(CONNECTION_SEND, {
					destination: DESTINATIONS.clientParams,
					headers: {},
					body: {}
				})
			};
		};
		const addSubscriptions = connection => {
			for (const action in SUBSCRIPTIONS){
				connection.addSubscription(`${SUBSCRIPTIONS[action]}${sessionId}`, response => {
					dispatch(action, response);
				});
			}
		};
		// //< FC-000181 - end

		initializeAfterCreation(
			commit,
			dispatch,
			connection,
			sessionId,
			setOpenCallback,
			addSubscriptions
		);
	},
	//<
	// [CONNECTION_CREATE]: ({commit, dispatch}, {clientCreator, connectionHeaders, sessionId}) => {

	// 	//< FC-000181 - begin
	// 	const connection = new Connection(clientCreator, connectionHeaders);
	// 	const setOpenCallback = connection => {
	// 		connection.openCallback = () => {
	// 			dispatch(CONNECTION_SEND, {
	// 				destination: DESTINATIONS.clientParams,
	// 				headers: {},
	// 				body: {}
	// 			})
	// 		};
	// 	};
	// 	const addSubscriptions = connection => {
	// 		for (const action in SUBSCRIPTIONS){
	// 			connection.addSubscription(`${SUBSCRIPTIONS[action]}${sessionId}`, response => {
	// 				dispatch(action, response);
	// 			});
	// 		}
	// 	};
	// 	//< FC-000181 - end

	// 	initializeAfterCreation(
	// 		commit,
	// 		dispatch,
	// 		connection,
	// 		sessionId,
	// 		setOpenCallback,
	// 		addSubscriptions
	// 	);
	// },
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
