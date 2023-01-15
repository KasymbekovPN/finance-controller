const mutateOnSetUserProfile = (state, data) => {
	state.username = data.username;
};

export {
	mutateOnSetUserProfile
};
