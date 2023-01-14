import { LS_KEYS } from "../../sconst/l-storage";
import { AUTH_STATUS_ERROR, AUTH_STATUS_LOADING, AUTH_STATUS_SUCCESS } from "../sconst/auth";

const mutateOnLoginRequest = state => {
	state.authStatus = AUTH_STATUS_LOADING;
};

const mutateOnLoginSuccess = (state, ls, response) => {
	ls.setItem(LS_KEYS.userToken, response.token);
	state.authenticated = true;
	state.authStatus = AUTH_STATUS_SUCCESS;
	state.token = response.token;
	state.hasLoadedOnce = true;
};

const mutateOnLoginError = (state, ls) => {
	ls.removeItem(LS_KEYS.userToken);
	state.authenticated = false;
	state.authStatus = AUTH_STATUS_ERROR;
	state.token = '';
	state.hasLoadedOnce = true;
};

export {
	mutateOnLoginRequest,
	mutateOnLoginSuccess,
	mutateOnLoginError
};
