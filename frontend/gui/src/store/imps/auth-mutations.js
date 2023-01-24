import { LOCAL_STORAGE_KEYS } from "../../sconst/localStorageKeys";
import { AUTH } from "../../sconst/auth";

const mutateOnLoginRequest = state => {
	state.authStatus = AUTH.STATUS.LOADING;
};

const mutateOnLoginSuccess = (state, ls, response) => {
	ls.setItem(LOCAL_STORAGE_KEYS.USER_TOKEN, response.token);
	state.authenticated = true;
	state.authStatus = AUTH.STATUS.SUCCESS;
	state.token = response.token;
};

const mutateOnLoginError = (state, ls) => {
	ls.removeItem(LOCAL_STORAGE_KEYS.USER_TOKEN);
	state.authenticated = false;
	state.authStatus = AUTH.STATUS.ERROR;
	state.token = '';
};

const mutateOnLogoutRequest = state => {
	state.authStatus = AUTH.STATUS.LOGOUT;
};

const mutateOnLogoutSuccess = (state, ls) => {
	ls.removeItem(LOCAL_STORAGE_KEYS.USER_TOKEN);
	state.authenticated = false;
	state.authStatus = AUTH.STATUS.SUCCESS;
	state.token = '';
};

const mutateOnLogoutError = (state, ls) => {
	ls.removeItem(LOCAL_STORAGE_KEYS.USER_TOKEN);
	state.authenticated = false;
	state.authStatus = AUTH.STATUS.ERROR;
	state.token = '';
};

const mutateAuthOnDisconnection = (state, ls) => {
	ls.removeItem(LOCAL_STORAGE_KEYS.USER_TOKEN);
	state.authenticated = false;
	state.authStatus = AUTH.STATUS.DISCONNECTED;
	state.token = '';
};

export {
	mutateOnLoginRequest,
	mutateOnLoginSuccess,
	mutateOnLoginError,
	mutateOnLogoutRequest,
	mutateOnLogoutSuccess,
	mutateOnLogoutError,
	mutateAuthOnDisconnection
};
