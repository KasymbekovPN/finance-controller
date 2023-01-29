import config from "../../../config";
import { AUTH } from "../../sconst/auth";
import { CONNECTION } from "../../sconst/connection";
import { USER } from "../../sconst/userProfile";

const requestLogin = ({commit, dispatch}, user) => {
	commit(AUTH.LOGIN.REQUEST);
	dispatch(CONNECTION.SEND, {
		destination: config.requests.auth,
		headers: {},
		body: user
	});
};

const responseLogin = ({commit, dispatch, router}, response) => {
	if (response.success){
		commit(AUTH.LOGIN.SUCCESS, response);
	} else {
		commit(AUTH.LOGIN.ERROR);
	}
	dispatch(USER.PROFILE.SET, response);
	router.push(config.paths.home);
};

const requestLogout = ({commit, dispatch}) => {
	commit(AUTH.LOGOUT.REQUEST);
	dispatch(CONNECTION.SEND, {
		destination: config.requests.logout,
		headers: {},
		body: {}
	});
};

const responseLogout = ({commit, dispatch, router}, response) => {
	if (response.success){
		commit(AUTH.LOGOUT.SUCCESS, response);
	} else {
		commit(AUTH.LOGOUT.ERROR);
	}
	dispatch(USER.PROFILE.RESET, response);
	router.push(config.paths.login);
};

const actOnDisconnected = ({commit, router}) => {
	commit(AUTH.ON.DISCONNECTED);
	router.push(config.paths.login);
};

export {
	requestLogin,
	responseLogin,
	requestLogout,
	responseLogout,
	actOnDisconnected
};
