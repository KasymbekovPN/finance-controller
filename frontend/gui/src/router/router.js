import vHomePage from '../components/v-home-page';
import vLoginPage from '../components/v-login-page';
import store from "../store/store";
import {
	createRouter,
	createWebHashHistory
} from "vue-router";
import config from '../../config';

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
		}
	]
});
