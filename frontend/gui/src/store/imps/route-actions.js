import { ROUTE } from "../../sconst/route";

const actOnRouteChanging = ({commit}, route) => {
	commit(ROUTE.ON.CHANGING, route);
};

export {
	actOnRouteChanging
};
