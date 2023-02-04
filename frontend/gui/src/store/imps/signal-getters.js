
const isActionModalVisible = state => {
	return !!state.actionModalVisible;
};

const isPaymentModalVisible = state => {
	return !!state.paymentModalVisible;
};

const isSellerModalVisible = state => {
	return !!state.sellerModalVisible;
};

const isAddressModalVisible = state => {
	return !!state.addressModalVisible;
};

const isStreetModalVisible = state => {
	return !!state.streetModalVisible;
};

const isCityModalVisible = state => {
	return !!state.cityModalVisible;
};

const isRegionModalVisible = state => {
	return !!state.regionModalVisible;
};

const isCountryModalVisible = state => {
	return !!state.countryModalVisible;
};

const isProductModalVisible = state => {
	return !!state.productModalVisible;
};

const isTagModalVisible = state => {
	return !!state.tagModalVisible;
};

export {
	isActionModalVisible,
	isPaymentModalVisible,
	isSellerModalVisible,
	isAddressModalVisible,
	isStreetModalVisible,
	isCityModalVisible,
	isRegionModalVisible,
	isCountryModalVisible,
	isProductModalVisible,
	isTagModalVisible
};
