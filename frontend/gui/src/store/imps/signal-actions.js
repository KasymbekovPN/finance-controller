import { SIGNAL } from "../../sconst/signal";

const actOnTagAddModelShow = ({commit}) => {
	commit(SIGNAL.MODAL.TAG.ADD.SHOW);
};

const actOnTagAddModelHide = ({commit}) => {
	commit(SIGNAL.MODAL.TAG.ADD.HIDE);
};

export {
	actOnTagAddModelShow,
	actOnTagAddModelHide
};
