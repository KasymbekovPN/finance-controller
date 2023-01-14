import { LS_KEYS } from "../../../src/sconst/l-storage";
import { mutateOnLoginError, mutateOnLoginRequest, mutateOnLoginSuccess } from "../../../src/store/imps/auth-mutations";
import { AUTH_STATUS_ERROR, AUTH_STATUS_LOADING, AUTH_STATUS_SUCCESS } from "../../../src/store/sconst/auth";

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
		const exprectedState = {authStatus: AUTH_STATUS_LOADING};

		let state = {};
		mutateOnLoginRequest(state);
		expect(state).toStrictEqual(exprectedState);
	});

	test('should check login success mutation', () => {
		const response = {token: TOKEN};
		const exprectedState = {
			authenticated: true,
			authStatus: AUTH_STATUS_SUCCESS,
			token: TOKEN,
			hasLoadedOnce: true
		};

		const ls = new LStorage();
		let state = {};
		mutateOnLoginSuccess(state, ls, response);
		expect(state).toStrictEqual(exprectedState);
		expect(ls.setKey).toBe(LS_KEYS.userToken);
		expect(ls.setValue).toBe(TOKEN);
	});

	test('should check login error mutation', () => {
		const exprectedState = {
			authenticated: false,
			authStatus: AUTH_STATUS_ERROR,
			token: '',
			hasLoadedOnce: true
		};

		const ls = new LStorage();
		let state = {};
		mutateOnLoginError(state, ls);
		expect(state).toStrictEqual(exprectedState);
		expect(ls.removedKey).toBe(LS_KEYS.userToken);
	});
});
