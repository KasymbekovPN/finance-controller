import vHomePage from '../components/v-home-page';
import vLoginPage from '../components/v-login-page';
import store from "../store/store";
import {
	createRouter,
	createWebHashHistory
} from "vue-router";
import { PATHS } from '@/sconst/paths';

const ifNotAuthenticated = (to, from, next) => {
	if (!store.getters.isAuthenticated){
		next();
		return
	}
	next(PATHS.HOME);
};

const ifAuthenticated = (to, from, next) => {
	if (store.getters.isAuthenticated){
		next();
		return;
	}
	next(PATHS.LOGIN);
};

export default createRouter({
	history: createWebHashHistory(),
	routes: [
		{
			path: PATHS.HOME,
			name: 'Home',
			component: vHomePage,
			beforeEnter: ifAuthenticated
		},
		{
			path: PATHS.LOGIN,
			name: 'Login',
			component: vLoginPage,
			beforeEnter: ifNotAuthenticated
		}
	]
});
