import Vuex from 'vuex';
import connection from './modules/connection';
import i18n from './modules/i18n';
import subscription from './modules/subscription';
import auth from './modules/auth';
import userProfile from './modules/userProfile';
import route from './modules/route';
import signal from './modules/signal';
import domain from './modules/domain';
import notification from './modules/notification';
import tag from './modules/tag';

export default new Vuex.Store({
	modules: {
		connection,
		i18n,
		subscription,
		auth,
		userProfile,
		route,
		signal,
		domain,
		notification,
		tag
	}
});
