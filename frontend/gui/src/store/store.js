import Vuex from "vuex";
import connection from "./modules/connection";
import clientParams from "./modules/clientParams";
import i18n from "./modules/i18n";
import subscription from "./modules/subscription";
import auth from "./modules/auth";
import userProfile from "./modules/userProfile";

export default new Vuex.Store({
	modules: {
		connection,
		clientParams,
		i18n,
		subscription,
		auth,
		userProfile
	}
});
