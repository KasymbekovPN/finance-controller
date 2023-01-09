import { USER_PROFILE_SET } from "../actions/userProfile";

const state = {
	username: ''
};

const getters = {
	username: state => state.username
};

const actions = {
	[USER_PROFILE_SET]: ({commit}, data) => {
		//<
		console.log('USER_PROFILE_SET data: ');
		console.log(data);
		//<
		commit(USER_PROFILE_SET, data);
	}
};

const mutations = {
	[USER_PROFILE_SET]: (state, data) => {
		state.username = data.username;
		//<
		console.log(`state.username: ${state.username}`);
		//<
	}
};

export default {
    state,
    getters,
    actions,
    mutations
};
