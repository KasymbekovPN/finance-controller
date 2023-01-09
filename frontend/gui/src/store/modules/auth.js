import {
	AUTH_LOGIN_ERROR,
	AUTH_LOGIN_REQUEST,
	AUTH_LOGIN_RESPONSE,
	AUTH_LOGIN_SUCCESS
} from "../actions/auth";
import { CONNECTION_SEND } from "../actions/connection";
import { USER_PROFILE_SET } from "../actions/userProfile";
import {
	AUTH_STATUS_ERROR,
	AUTH_STATUS_LOADING,
	AUTH_STATUS_SUCCESS
} from "../status/auth";

const state = {
	token: localStorage.getItem('user-token') || '',
	status: '',
	hasLoadedOnce: false
};

const getters = {
	isAuthenticated: state => !!state.token,
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
	}
};

const mutations = {
	[AUTH_LOGIN_REQUEST]: state => {
		state.status = AUTH_STATUS_LOADING;
	},
	[AUTH_LOGIN_SUCCESS]: (state, response) => {
		localStorage.setItem('user-token', response.token);
		state.status = AUTH_STATUS_SUCCESS;
		state.token = response.token;
		state.hasLoadedOnce = true;
	},
	[AUTH_LOGIN_ERROR]: state => {
		localStorage.removeItem('user-token');
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
