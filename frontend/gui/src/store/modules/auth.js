import { LS_KEYS } from "../../sconst/l-storage";
import router from "../../router/router";
import {
	AUTH_LOGIN_ERROR,
	AUTH_LOGIN_REQUEST,
	AUTH_LOGIN_RESPONSE,
	AUTH_LOGIN_SUCCESS
} from "../sconst/auth";
import { requestLogin, responseLogin } from "../imps/auth-actions";
import { getStatus, isAuthenticated } from "../imps/auth-getters";
import {
	mutateOnLoginRequest,
	mutateOnLoginSuccess,
	mutateOnLoginError
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
	[AUTH_LOGIN_RESPONSE]: ({commit, dispatch}, response) => { responseLogin({commit, dispatch, router}, response); }
};

const mutations = {
	[AUTH_LOGIN_REQUEST]: mutateOnLoginRequest,
	[AUTH_LOGIN_SUCCESS]: (state, response) => { mutateOnLoginSuccess(state, localStorage, response); },
	[AUTH_LOGIN_ERROR]: state => { mutateOnLoginError(state, localStorage); }
};

export default {
    state,
    getters,
    actions,
    mutations
};
