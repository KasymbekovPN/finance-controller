import { USER_PROFILE_SET } from "../sconst/userProfile";

const setUserProfile = ({commit}, data) => {
	commit(USER_PROFILE_SET, data);
};

export {
	setUserProfile
};
