import { NOTIFICATION } from "../../sconst/notification";

const state = {};

const getters = {};

const actions = {
	[NOTIFICATION.ERROR]: ({dispatch}, seed) => {
		//<
		console.log(dispatch);
		console.log(seed);
		//<
		//< impl it
	}
};

const mutations = {};

export default {
	state,
	getters,
	actions,
	mutations
};
