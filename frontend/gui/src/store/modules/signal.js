import { SIGNAL } from "../../sconst/signal";
import {
	isActionModalVisible,
	isAddressModalVisible,
	isCityModalVisible,
	isCountryModalVisible,
	isPaymentModalVisible,
	isProductModalVisible,
	isRegionModalVisible,
	isSellerModalVisible,
	isStreetModalVisible,
	isTagModalVisible
} from "../imps/signal-getters";
import {
	actOnSomeModalHide,
	actOnSomeModalShow
} from "../imps/signal-actions";
import {
	mutateOnSomeModalHide,
	mutateOnSomeModalShow
} from "../imps/signal-mutations";

const state = {
	actionModalVisible: false,
	paymentModalVisible: false,
	sellerModalVisible: false,
	addressModalVisible: false,
	streetModalVisible: false,
	cityModalVisible: false,
	regionModalVisible: false,
	countryModalVisible: false,
	productModalVisible: false,
	tagModalVisible: false
};

const getters = {
	isActionModalVisible: isActionModalVisible,
	isPaymentModalVisible: isPaymentModalVisible,
	isSellerModalVisible: isSellerModalVisible,
	isAddressModalVisible: isAddressModalVisible,
	isStreetModalVisible: isStreetModalVisible,
	isCityModalVisible: isCityModalVisible,
	isRegionModalVisible: isRegionModalVisible,
	isCountryModalVisible: isCountryModalVisible,
	isProductModalVisible: isProductModalVisible,
	isTagModalVisible: isTagModalVisible
};

const actions = {
	[SIGNAL.MODAL.SOME.ADD.SHOW]: actOnSomeModalShow,
	[SIGNAL.MODAL.SOME.ADD.HIDE]: actOnSomeModalHide
};

const mutations = {
	[SIGNAL.MODAL.SOME.ADD.SHOW]: mutateOnSomeModalShow,
	[SIGNAL.MODAL.SOME.ADD.HIDE]: mutateOnSomeModalHide
};

export default {
    state,
    getters,
    actions,
    mutations
};
