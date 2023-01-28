import {
	createRouter,
	createWebHashHistory
} from "vue-router";
import config from '../../config';
import store from "../store/store";
import vHomePage from '../components/v-home-page';
import vLoginPage from '../components/v-login-page';
import vActions from '../components/v-actions';
import vAddresses from '../components/v-addresses';
import vCities from '../components/v-cities';
import vCountries from '../components/v-countries';
import vPayments from '../components/v-payments';
import vProducts from '../components/v-products';
import vRegions from '../components/v-regions';
import vSellers from '../components/v-sellers';
import vStreets from '../components/v-streets';
import vTags from '../components/v-tags';

const ifNotAuthenticated = (to, from, next) => {
	if (!store.getters.isAuthenticated){
		next();
		return
	}
	next(config.paths.home);
};

const ifAuthenticated = (to, from, next) => {
	if (store.getters.isAuthenticated){
		next();
		return;
	}
	next(config.paths.login);
};

export default createRouter({
	history: createWebHashHistory(),
	routes: [
		{
			path: config.paths.home,
			name: 'Home',
			component: vHomePage,
			beforeEnter: ifAuthenticated
		},
		{
			path: config.paths.login,
			name: 'Login',
			component: vLoginPage,
			beforeEnter: ifNotAuthenticated
		},
		{
			path: config.paths.actions,
			name: "Actions",
			component: vActions,
			beforeEnter: ifAuthenticated
		},
		{
			path: config.paths.addresses,
			name: "Addresses",
			component: vAddresses,
			beforeEnter: ifAuthenticated
		},
		{
			path: config.paths.cities,
			name: "Cities",
			component: vCities,
			beforeEnter: ifAuthenticated
		},
		{
			path: config.paths.countries,
			name: "Countries",
			component: vCountries,
			beforeEnter: ifAuthenticated
		},
		{
			path: config.paths.payments,
			name: "Payments",
			component: vPayments,
			beforeEnter: ifAuthenticated
		},
		{
			path: config.paths.products,
			name: "Products",
			component: vProducts,
			beforeEnter: ifAuthenticated
		},
		{
			path: config.paths.regions,
			name: "Regions",
			component: vRegions,
			beforeEnter: ifAuthenticated
		},
		{
			path: config.paths.sellers,
			name: "Sellers",
			component: vSellers,
			beforeEnter: ifAuthenticated
		},
		{
			path: config.paths.streets,
			name: "Streets",
			component: vStreets,
			beforeEnter: ifAuthenticated
		},
		{
			path: config.paths.tags,
			name: "Tags",
			component: vTags,
			beforeEnter: ifAuthenticated
		}
	]
});
