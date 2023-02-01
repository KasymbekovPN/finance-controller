import { SIGNAL } from "../../sconst/signal";
import { isTagAddModalVisible } from "../imps/signal-getters";
import {
	actOnTagAddModelHide,
	actOnTagAddModelShow
} from "../imps/signal-actions";
import {
	mutateOnTagAddModelHide,
	mutateOnTagAddModelShow
} from "../imps/signal-mutations";

const state = {
	tagAddModalVisible: false
};

const getters = {
	isTagAddModalVisible: isTagAddModalVisible
};

const actions = {
	[SIGNAL.MODAL.TAG.ADD.SHOW]: actOnTagAddModelShow,
	[SIGNAL.MODAL.TAG.ADD.HIDE]: actOnTagAddModelHide
};

const mutations = {
	[SIGNAL.MODAL.TAG.ADD.SHOW]: mutateOnTagAddModelShow,
	[SIGNAL.MODAL.TAG.ADD.HIDE]: mutateOnTagAddModelHide
};

export default {
    state,
    getters,
    actions,
    mutations
};
