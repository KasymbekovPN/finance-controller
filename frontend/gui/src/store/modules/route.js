import { ROUTE } from "../../sconst/route";
import { actOnRouteChanging } from "../imps/route-actions";
import {
	isActionsRoute,
	isAddressesRoute,
	isCitiesRoute,
	isCountriesRoute,
	isPaymentsRoute,
	isProductsRoute,
	isRegionsRoute,
	isSellersRoute,
	isStreetsRoute,
	isTagsRoute,
	isHomeRoute
} from "../imps/route-getters";
import { mutateOnRouteChanging } from "../imps/route-mutations";

const state = {
	route: undefined
};

const getters = {
	isTagsRoute: isTagsRoute,
	isProductsRoute: isProductsRoute,
	isCountriesRoute: isCountriesRoute,
	isRegionsRoute: isRegionsRoute,
	isCitiesRoute: isCitiesRoute,
	isStreetsRoute: isStreetsRoute,
	isAddressesRoute: isAddressesRoute,
	isSellersRoute: isSellersRoute,
	isPaymentsRoute: isPaymentsRoute,
	isActionsRoute: isActionsRoute,
	isHomeRoute: isHomeRoute
};

const actions = {
	[ROUTE.ON.CHANGING]: actOnRouteChanging
};

const mutations = {
	[ROUTE.ON.CHANGING]: mutateOnRouteChanging
};

export default {
    state,
    getters,
    actions,
    mutations
};
