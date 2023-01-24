import { LOCAL_STORAGE_KEYS } from "../../../src/sconst/localStorageKeys";
import {
	mutateAuthOnDisconnection,
	mutateOnLoginError,
	mutateOnLoginRequest,
	mutateOnLoginSuccess,
	mutateOnLogoutError,
	mutateOnLogoutRequest,
	mutateOnLogoutSuccess
} from "../../../src/store/imps/auth-mutations";
import { AUTH } from "../../../src/sconst/auth";

describe('auth-mutations.js', () => {
	const TOKEN = 'some token';

	class LStorage {

		setItem(key, value){
			this.setKey = key;
			this.setValue = value;
		}

		removeItem(key){
			this.removedKey = key;
		}
	};

	test('should check login request mutation', () => {
		const exprectedState = {authStatus: AUTH.STATUS.LOADING};

		let state = {};
		mutateOnLoginRequest(state);
		expect(state).toStrictEqual(exprectedState);
	});

	test('should check login success mutation', () => {
		const response = {token: TOKEN};
		const exprectedState = {
			authenticated: true,
			authStatus: AUTH.STATUS.SUCCESS,
			token: TOKEN
		};

		const ls = new LStorage();
		let state = {};
		mutateOnLoginSuccess(state, ls, response);
		expect(state).toStrictEqual(exprectedState);
		expect(ls.setKey).toBe(LOCAL_STORAGE_KEYS.USER_TOKEN);
		expect(ls.setValue).toBe(TOKEN);
	});

	test('should check login error mutation', () => {
		const exprectedState = {
			authenticated: false,
			authStatus: AUTH.STATUS.ERROR,
			token: ''
		};

		const ls = new LStorage();
		let state = {};
		mutateOnLoginError(state, ls);
		expect(state).toStrictEqual(exprectedState);
		expect(ls.removedKey).toBe(LOCAL_STORAGE_KEYS.USER_TOKEN);
	});


	test('should check logout request mutation', () => {
		const exprectedState = {authStatus: AUTH.STATUS.LOGOUT};

		let state = {};
		mutateOnLogoutRequest(state);
		expect(state).toStrictEqual(exprectedState);
	});

	test('should check logout success mutation', () => {
		const exprectedState = {
			authenticated: false,
			authStatus: AUTH.STATUS.SUCCESS,
			token: ''
		};

		const ls = new LStorage();
		let state = {};
		mutateOnLogoutSuccess(state, ls);
		expect(state).toStrictEqual(exprectedState);
		expect(ls.removedKey).toBe(LOCAL_STORAGE_KEYS.USER_TOKEN);
	});

	test('should check logout error mutation', () => {
		const exprectedState = {
			authenticated: false,
			authStatus: AUTH.STATUS.ERROR,
			token: ''
		};

		const ls = new LStorage();
		let state = {};
		mutateOnLogoutError(state, ls);
		expect(state).toStrictEqual(exprectedState);
		expect(ls.removedKey).toBe(LOCAL_STORAGE_KEYS.USER_TOKEN);
	});

	test('should check disconnection mutation', () => {
		const exprectedState = {
			authenticated: false,
			authStatus: AUTH.STATUS.DISCONNECTED,
			token: ''
		};

		const ls = new LStorage();
		let state = {};
		mutateAuthOnDisconnection(state, ls);
		expect(state).toStrictEqual(exprectedState);
		expect(ls.removedKey).toBe(LOCAL_STORAGE_KEYS.USER_TOKEN);
	});
});
