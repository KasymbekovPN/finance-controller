import {
	USER_PROFILE_RESET,
	USER_PROFILE_SET
} from "../sconst/userProfile";

const setUserProfile = ({commit}, data) => {
	commit(USER_PROFILE_SET, data);
};

const resetUserProfile = ({commit}) => {
	commit(USER_PROFILE_RESET);
};

export {
	setUserProfile,
	resetUserProfile
};
