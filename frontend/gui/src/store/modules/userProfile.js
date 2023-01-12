import { USER_PROFILE_SET } from "../sconst/userProfile";

const state = {
	username: ''
};

const getters = {
	username: state => state.username
};

const actions = {
	[USER_PROFILE_SET]: ({commit}, data) => {
		commit(USER_PROFILE_SET, data);
	}
};

const mutations = {
	[USER_PROFILE_SET]: (state, data) => {
		state.username = data.username;
	}
};

export default {
    state,
    getters,
    actions,
    mutations
};
