import { PATHS } from "../../sconst/path";
import { DESTINATIONS } from "../../sconst/destinations";
import { AUTH_LOGIN_ERROR, AUTH_LOGIN_REQUEST, AUTH_LOGIN_SUCCESS, AUTH_LOGOUT_ERROR, AUTH_LOGOUT_REQUEST, AUTH_LOGOUT_SUCCESS } from "../sconst/auth";
import { CONNECTION_SEND } from "../sconst/connection";
import { USER_PROFILE_RESET, USER_PROFILE_SET } from "../sconst/userProfile";

const requestLogin = ({commit, dispatch}, user) => {
	commit(AUTH_LOGIN_REQUEST);
	dispatch(CONNECTION_SEND, {
		destination: DESTINATIONS.auth,
		headers: {},
		body: user
	});
};

const responseLogin = ({commit, dispatch, router}, response) => {
	if (response.success){
		commit(AUTH_LOGIN_SUCCESS, response);
	} else {
		commit(AUTH_LOGIN_ERROR);
	}
	dispatch(USER_PROFILE_SET, response);
	router.push(PATHS.home);
};

const requestLogout = ({commit, dispatch}) => {
	commit(AUTH_LOGOUT_REQUEST);
	dispatch(CONNECTION_SEND, {
		destination: DESTINATIONS.logout,
		headers: {},
		body: {}
	});
};

const responseLogout = ({commit, dispatch, router}, response) => {
	if (response.success){
		commit(AUTH_LOGOUT_SUCCESS, response);
	} else {
		commit(AUTH_LOGOUT_ERROR);
	}
	dispatch(USER_PROFILE_RESET, response);
	router.push(PATHS.login);
};

export {
	requestLogin,
	responseLogin,
	requestLogout,
	responseLogout
};
