import { LS_KEYS } from "../../sconst/l-storage";
import router from "../../router/router";
import {
	AUTH_LOGIN_ERROR,
	AUTH_LOGIN_REQUEST,
	AUTH_LOGIN_RESPONSE,
	AUTH_LOGIN_SUCCESS,
	AUTH_LOGOUT_ERROR,
	AUTH_LOGOUT_REQUEST,
	AUTH_LOGOUT_RESPONSE,
	AUTH_LOGOUT_SUCCESS
} from "../sconst/auth";
import { requestLogin, requestLogout, responseLogin, responseLogout } from "../imps/auth-actions";
import { getStatus, isAuthenticated } from "../imps/auth-getters";
import {
	mutateOnLoginRequest,
	mutateOnLoginSuccess,
	mutateOnLoginError,
	mutateOnLogoutRequest,
	mutateOnLogoutSuccess,
	mutateOnLogoutError

} from "../imps/auth-mutations";

const state = {
	authenticated: false,
	token: localStorage.getItem(LS_KEYS.userToken) || '',
	authStatus: ''
};

const getters = {
	isAuthenticated: isAuthenticated,
	authStatus: getStatus
};

const actions = {
	[AUTH_LOGIN_REQUEST]: ({commit, dispatch}, user) => {
		requestLogin({commit, dispatch}, user);
	},
	[AUTH_LOGIN_RESPONSE]: ({commit, dispatch}, response) => { responseLogin({commit, dispatch, router}, response); },
	[AUTH_LOGOUT_REQUEST]: requestLogout,
	[AUTH_LOGOUT_RESPONSE]: ({commit, dispatch}, response) => { responseLogout({commit, dispatch, router}, response); }
};

const mutations = {
	[AUTH_LOGIN_REQUEST]: mutateOnLoginRequest,
	[AUTH_LOGIN_SUCCESS]: (state, response) => { mutateOnLoginSuccess(state, localStorage, response); },
	[AUTH_LOGIN_ERROR]: state => { mutateOnLoginError(state, localStorage); },
	[AUTH_LOGOUT_REQUEST]: mutateOnLogoutRequest,
	[AUTH_LOGOUT_SUCCESS]: state => { mutateOnLogoutSuccess(state, localStorage); },
	[AUTH_LOGOUT_ERROR]: state => { mutateOnLogoutError(state, localStorage); }
};

export default {
    state,
    getters,
    actions,
    mutations
};
