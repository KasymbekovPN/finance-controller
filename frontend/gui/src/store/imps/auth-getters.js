const isAuthenticated = state => state.authenticated;
const getStatus = state => state.authStatus;

export {
	isAuthenticated,
	getStatus
};
