
const mutateOnConnectionCreation = (state, {connection, sessionId}) => {
	state.connection = connection;
	state.sessionId = sessionId;
};

const mutateOnConnection = state => {
	//< adjust args
	state.connection.connect();
};

const mutateOnDisconnection = state => {
	state.connection.disconnect();
};

const mutateOnSending = (state, {destination, headers, body}) => {
	state.connection.send(
		`/app${destination}/${state.sessionId}`,
		headers,
		JSON.stringify(body)
	);
};

export {
	mutateOnConnectionCreation,
	mutateOnConnection,
	mutateOnDisconnection,
	mutateOnSending
};
