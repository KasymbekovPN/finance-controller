
const isConnected = state => {
	return state.connection !== undefined && !!state.connection.connected;
};

export {
	isConnected
};
