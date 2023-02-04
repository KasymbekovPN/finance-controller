import { SIGNAL } from "../../sconst/signal";

const actOnSomeModalShow = ({commit}, route) => {
	commit(SIGNAL.MODAL.SOME.ADD.SHOW, route);
};

const actOnSomeModalHide = ({commit}, route) => {
	commit(SIGNAL.MODAL.SOME.ADD.HIDE, route);
};

export {
	actOnSomeModalShow,
	actOnSomeModalHide
};
