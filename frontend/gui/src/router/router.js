import vHomePage from '@/components/v-home-page';
import vLoginPage from '@/components/v-login-page';
import store from "@/store/store";
import { createRouter, createWebHashHistory } from "vue-router";

const ifNotAuthenticated = (to, from, next) => {
	if (!store.getters.isAuthenticated){
		next();
		return
	}
	next('/');
};

const ifAuthenticated = (to, from, next) => {
	//<
	console.log(0);
	//<
	if (store.getters.isAuthenticated){
	//<
	console.log(1);
	//<
		next();
		return;
	}
	//<
	console.log(2);
	//<
	next('/login');
};

export default createRouter({
	history: createWebHashHistory(),
	routes: [
		{
			path: '/',
			name: 'Home',
			component: vHomePage,
			beforeEnter: ifAuthenticated
		},
		{
			path: '/login',
			name: 'Login',
			component: vLoginPage,
			beforeEnter: ifNotAuthenticated
		}
	]
});
