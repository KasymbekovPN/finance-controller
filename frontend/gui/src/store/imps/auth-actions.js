import { PATHS } from "../../sconst/paths";
import { DESTINATIONS } from "../../sconst/destinations";
import { AUTH } from "../../sconst/auth";
import { CONNECTION } from "../../sconst/connection";
import { USER } from "../../sconst/userProfile";

const requestLogin = ({commit, dispatch}, user) => {
	commit(AUTH.LOGIN.REQUEST);
	dispatch(CONNECTION.SEND, {
		destination: DESTINATIONS.AUTH,
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
	router.push(PATHS.HOME);
};

const requestLogout = ({commit, dispatch}) => {
	commit(AUTH.LOGOUT.REQUEST);
	dispatch(CONNECTION.SEND, {
		destination: DESTINATIONS.LOGOUT,
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
	router.push(PATHS.LOGIN);
};

export {
	requestLogin,
	responseLogin,
	requestLogout,
	responseLogout
};
