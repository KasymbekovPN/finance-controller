import { LS_KEYS } from "../../sconst/l-storage";
import {
	AUTH_LOGOUT_ERROR,
	AUTH_LOGOUT_SUCCESS,
	AUTH_STATUS_ERROR,
	AUTH_STATUS_LOADING,
	AUTH_STATUS_LOGOUT,
	AUTH_STATUS_SUCCESS
} from "../sconst/auth";

const mutateOnLoginRequest = state => {
	state.authStatus = AUTH_STATUS_LOADING;
};

const mutateOnLoginSuccess = (state, ls, response) => {
	ls.setItem(LS_KEYS.userToken, response.token);
	state.authenticated = true;
	state.authStatus = AUTH_STATUS_SUCCESS;
	state.token = response.token;
};

const mutateOnLoginError = (state, ls) => {
	ls.removeItem(LS_KEYS.userToken);
	state.authenticated = false;
	state.authStatus = AUTH_STATUS_ERROR;
	state.token = '';
};

const mutateOnLogoutRequest = state => {
	state.authStatus = AUTH_STATUS_LOGOUT;
};

const mutateOnLogoutSuccess = (state, ls) => {
	ls.removeItem(LS_KEYS.userToken);
	state.authenticated = false;
	state.authStatus = AUTH_LOGOUT_SUCCESS;
	state.token = '';
};

const mutateOnLogoutError = (state, ls) => {
	ls.removeItem(LS_KEYS.userToken);
	state.authenticated = false;
	state.authStatus = AUTH_LOGOUT_ERROR;
	state.token = '';
};

export {
	mutateOnLoginRequest,
	mutateOnLoginSuccess,
	mutateOnLoginError,
	mutateOnLogoutRequest,
	mutateOnLogoutSuccess,
	mutateOnLogoutError
};
