import {
	resetUserProfile,
	setUserProfile
} from "../imps/userProfile-actions";
import { getUserName } from "../imps/userProfile-getters";
import {
	mutateOnResetUserProfile,
	mutateOnSetUserProfile
} from "../imps/userProfile-mutations";
import { USER } from "../../sconst/userProfile";

const state = {
	username: ''
};

const getters = {
	username: getUserName
};

const actions = {
	[USER.PROFILE.SET]: setUserProfile,
	[USER.PROFILE.RESET]: resetUserProfile
};

const mutations = {
	[USER.PROFILE.SET]: mutateOnSetUserProfile,
	[USER.PROFILE.RESET]: mutateOnResetUserProfile
};

export default {
    state,
    getters,
    actions,
    mutations
};
