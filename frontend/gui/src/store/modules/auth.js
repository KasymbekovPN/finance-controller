import router from "../../router/router";
import {
	AUTH_LOGIN_ERROR,
	AUTH_LOGIN_REQUEST,
	AUTH_LOGIN_RESPONSE,
	AUTH_LOGIN_SUCCESS,
	AUTH_STATUS_ERROR,
	AUTH_STATUS_LOADING,
	AUTH_STATUS_SUCCESS
} from "../sconst/auth";
import { CONNECTION_SEND } from "../sconst/connection";
import { USER_PROFILE_SET } from "../sconst/userProfile";

const state = {
	authenticated: false,
	token: localStorage.getItem('user-token') || '',
	status: '',
	hasLoadedOnce: false
};

const getters = {
	isAuthenticated: state => state.authenticated,
	authStatus: state => state.status
};

const actions = {
	[AUTH_LOGIN_REQUEST]: ({commit, dispatch}, user) => {
		commit(AUTH_LOGIN_REQUEST);
		dispatch(CONNECTION_SEND, {
			destination: '/authRequest',
			headers: {},
			body: user
		});
	},
	[AUTH_LOGIN_RESPONSE]: ({commit, dispatch}, response) => {
		if (response.success){
			commit(AUTH_LOGIN_SUCCESS, response);
		} else {
			commit(AUTH_LOGIN_ERROR);
		}
		dispatch(USER_PROFILE_SET, response);
		router.push('/');
	}
};

const mutations = {
	[AUTH_LOGIN_REQUEST]: state => {
		state.status = AUTH_STATUS_LOADING;
	},
	[AUTH_LOGIN_SUCCESS]: (state, response) => {
		localStorage.setItem('user-token', response.token);
		state.authenticated = true;
		state.status = AUTH_STATUS_SUCCESS;
		state.token = response.token;
		state.hasLoadedOnce = true;
	},
	[AUTH_LOGIN_ERROR]: state => {
		localStorage.removeItem('user-token');
		state.authenticated = false;
		state.status = AUTH_STATUS_ERROR;
		state.token = '';
		state.hasLoadedOnce = true;
	}
};

export default {
    state,
    getters,
    actions,
    mutations
};
