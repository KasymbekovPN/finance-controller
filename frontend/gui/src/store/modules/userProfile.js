import { setUserProfile } from "../imps/userProfile-actions";
import { getUserName } from "../imps/userProfile-getters";
import { mutateOnSetUserProfile } from "../imps/userProfile-mutations";
import { USER_PROFILE_SET } from "../sconst/userProfile";

const state = {
	username: ''
};

const getters = {
	username: getUserName
};

const actions = {
	[USER_PROFILE_SET]: setUserProfile
};

const mutations = {
	[USER_PROFILE_SET]: mutateOnSetUserProfile
};

export default {
    state,
    getters,
    actions,
    mutations
};
