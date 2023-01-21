import {
	resetUserProfile,
	setUserProfile
} from "../imps/userProfile-actions";
import { getUserName } from "../imps/userProfile-getters";
import {
	mutateOnResetUserProfile,
	mutateOnSetUserProfile
} from "../imps/userProfile-mutations";
import {
	USER_PROFILE_RESET,
	USER_PROFILE_SET
} from "../sconst/userProfile";

const state = {
	username: ''
};

const getters = {
	username: getUserName
};

const actions = {
	[USER_PROFILE_SET]: setUserProfile,
	[USER_PROFILE_RESET]: resetUserProfile
};

const mutations = {
	[USER_PROFILE_SET]: mutateOnSetUserProfile,
	[USER_PROFILE_RESET]: mutateOnResetUserProfile
};

export default {
    state,
    getters,
    actions,
    mutations
};
