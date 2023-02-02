
const mutateOnTagAddModelShow = state => {
	//<
	console.log('show tag');
	//
	state.tagAddModalVisible = true;
};

const mutateOnTagAddModelHide = state => {
	//<
	console.log('hide tag');
	//<
	state.tagAddModalVisible = false;
};

export {
	mutateOnTagAddModelShow,
	mutateOnTagAddModelHide
};
