import { LOCAL_STORAGE_KEYS } from "../../sconst/localStorageKeys";
import router from "../../router/router";
import { AUTH } from "../../sconst/auth";
import {
	requestLogin,
	requestLogout,
	responseLogin,
	responseLogout
} from "../imps/auth-actions";
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
	token: localStorage.getItem(LOCAL_STORAGE_KEYS.USER_TOKEN) || '',
	authStatus: ''
};

const getters = {
	isAuthenticated: isAuthenticated,
	authStatus: getStatus
};

const actions = {
	[AUTH.LOGIN.REQUEST]: ({commit, dispatch}, user) => {
		requestLogin({commit, dispatch}, user);
	},
	[AUTH.LOGIN.RESPONSE]: ({commit, dispatch}, response) => { responseLogin({commit, dispatch, router}, response); },
	[AUTH.LOGOUT.REQUEST]: requestLogout,
	[AUTH.LOGOUT.RESPONSE]: ({commit, dispatch}, response) => { responseLogout({commit, dispatch, router}, response); }
};

const mutations = {
	[AUTH.LOGIN.REQUEST]: mutateOnLoginRequest,
	[AUTH.LOGIN.SUCCESS]: (state, response) => { mutateOnLoginSuccess(state, localStorage, response); },
	[AUTH.LOGIN.ERROR]: state => { mutateOnLoginError(state, localStorage); },
	[AUTH.LOGOUT.REQUEST]: mutateOnLogoutRequest,
	[AUTH.LOGOUT.SUCCESS]: state => { mutateOnLogoutSuccess(state, localStorage); },
	[AUTH.LOGOUT.ERROR]: state => { mutateOnLogoutError(state, localStorage); }
};

export default {
    state,
    getters,
    actions,
    mutations
};
