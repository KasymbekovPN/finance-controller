import config from "../../../config";
import {
	processAuthRequestSubscription,
	processClientParamsSubscription,
	processI18nSubscription,
	processLogoutRequestSubscription
} from "../../../src/store/imps/subscription-actions";
import { CONNECTION } from "../../../src/sconst/connection";
import { I18N } from "../../../src/sconst/i18n";
import { AUTH } from "../../../src/sconst/auth";

describe('subscription-actions.js', () => {

	const response = {
		body: '{\"locale\":\"en\"}'
	};

	let dispatchResult = {};
	const dispatch = (command, data) => {
		dispatchResult[command] = data;
	};

	const reset = () => {
		dispatchResult = {};
	};

	test('should check clientParams subscription action ', () => {
		const clientParams = JSON.parse(response.body);
		const expectedDispatchResult = {
			[I18N.SET.LOCALE]: clientParams.locale,
			[CONNECTION.SEND]: {
				destination: config.requests.i18n,
				headers: {},
				body: {}
			}
		};

		processClientParamsSubscription({dispatch}, response);
		expect(dispatchResult).toStrictEqual(expectedDispatchResult);
		reset();
	});

	test('should check i18n subscription action ', () => {
		const data = JSON.parse(response.body);
		const expectedDispatchResult = {
			[I18N.SET.TEMPLATES]: data
		};

		processI18nSubscription({dispatch}, response);
		expect(dispatchResult).toStrictEqual(expectedDispatchResult);
		reset();
	});

	test('should check authRequest subscription action ', () => {
		const data = JSON.parse(response.body);
		const expectedDispatchResult = {
			[AUTH.LOGIN.RESPONSE]: data
		};

		processAuthRequestSubscription({dispatch}, response);
		expect(dispatchResult).toStrictEqual(expectedDispatchResult);
		reset();
	});

	test('should check logoutRequest subscription action ', () => {
		const data = JSON.parse(response.body);
		const expectedDispatchResult = {
			[AUTH.LOGOUT.RESPONSE]: data
		};

		processLogoutRequestSubscription({dispatch}, response);
		expect(dispatchResult).toStrictEqual(expectedDispatchResult);
		reset();
	});
});
