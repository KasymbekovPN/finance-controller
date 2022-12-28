import Vuex from "vuex";
import connection from "./modules/connection";
import clientParams from "./modules/clientParams";

export default new Vuex.Store({
	modules: {
		connection,
		clientParams
	}
});
