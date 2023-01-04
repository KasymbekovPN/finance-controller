import { CONNECTION_SEND } from "../../src/store/actions/connection";
import {
	loadParams,
	loadI18n
} from "../../src/loadManager/loadManager";

class Store {
	dispatch(command, object){
		this.dispatchCommand = command;
		this.dispatchObject = object;
	}
}

describe('loadManager.js', () => {
	test('should check loadParams-method', () => {
		const st = new Store();
		loadParams(st);

		expect(st.dispatchCommand).toBe(CONNECTION_SEND);
		expect(st.dispatchObject).toStrictEqual({
			destination: '/clientParamsRequest',
			headers: {},
			body: {}
		});
	});

	test('should check loadI18n-method', () => {
		const st = new Store();
		loadI18n(st);

		expect(st.dispatchCommand).toBe(CONNECTION_SEND);
		expect(st.dispatchObject).toStrictEqual({
			destination: '/i18nRequest',
			headers: {},
			body: {}
		});
	});
})
