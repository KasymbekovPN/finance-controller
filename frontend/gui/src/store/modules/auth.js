import { AUTH_LOGIN_REQUEST } from "../actions/auth";
import { CONNECTION_SEND } from "../actions/connection";

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
	[AUTH_LOGIN_REQUEST]: ({dispatch}, {user}) => {
		dispatch(CONNECTION_SEND, {
			destination: '/authRequest',
			headers: {},
			body: user
		});
	}
	//<
//     [AUTH_REQUEST]: ({ commit, dispatch }, user) => {
//         return new Promise((resolve, reject) => {
//             commit(AUTH_REQUEST);
//             apiCall({ url: "auth", data: user, method: "POST"})
//                 .then(resp => {
// 					console.log('auth | actions | AUTH_REQUEST: then');
//                     localStorage.setItem('user-token', resp.token);
//                     commit(AUTH_SUCCESS, resp);
//                     dispatch(USER_REQUEST);
//                     resolve(resp);
//                 })
//                 .catch(err => {
// 					console.log('auth | actions | AUTH_REQUEST: catch');
//                     commit(AUTH_ERROR, err);
//                     localStorage.removeItem("user-token");
//                     reject(err);
//                 });
//         });
//     },
//     [AUTH_LOGOUT]: ({ commit }) => {
//         return new Promise(resolve => {
// 			console.log('auth | actions | AUTH_LOGOUT');
//             commit(AUTH_LOGOUT);
//             localStorage.removeItem("user-token");
//             resolve();
//         });
//     }
};

const mutations = {
//     [AUTH_REQUEST]: state => {
// 		console.log('auth | mutations | AUTH_REQUEST');
//         state.status = "loading";
//     },
//     [AUTH_SUCCESS]: (state, resp) => {
// 		console.log('auth | mutations | AUTH_SUCCESS');
//         state.status = "success";
//         state.token = resp.token;
//         state.hasLoadedOnce = true;
//     },
//     [AUTH_ERROR]: state => {
// 		console.log('auth | mutations | AUTH_ERROR');
//         state.status = "error";
//         state.hasLoadedOnce = true;
//     },
//     [AUTH_LOGOUT]: state => {
// 		console.log('auth | mutations | AUTH_LOGOUT');
//         state.token = "";
//     }
};

export default {
    state,
    getters,
    actions,
    mutations
};
