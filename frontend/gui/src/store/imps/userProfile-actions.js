import { USER } from "../../sconst/userProfile";

const setUserProfile = ({commit}, data) => {
	commit(USER.PROFILE.SET, data);
};

const resetUserProfile = ({commit}) => {
	commit(USER.PROFILE.RESET);
};

export {
	setUserProfile,
	resetUserProfile
};
