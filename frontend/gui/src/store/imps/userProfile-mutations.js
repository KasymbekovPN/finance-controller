const mutateOnSetUserProfile = (state, data) => {
	state.username = data.username;
};

const mutateOnResetUserProfile = state => {
	state.username = '';
};

export {
	mutateOnSetUserProfile,
	mutateOnResetUserProfile
};
