import {
	processAuthRequestSubscription,
	processClientParamsSubscription,
	processI18nSubscription
} from "../../../src/store/imps/subscription-actions";
import { DESTINATIONS } from "../../../src/sconst/destinationas";
import { CONNECTION_SEND } from "../../../src/store/sconst/connection";
import { I18N_SET_LOCALE, I18N_SET_TEMPLATES } from "../../../src/store/sconst/i18n";
import { AUTH_LOGIN_RESPONSE } from "../../../src/store/sconst/auth";

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
			[I18N_SET_LOCALE]: clientParams.locale,
			[CONNECTION_SEND]: {
				destination: DESTINATIONS.i18n,
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
			[I18N_SET_TEMPLATES]: data
		};

		processI18nSubscription({dispatch}, response);
		expect(dispatchResult).toStrictEqual(expectedDispatchResult);
		reset();
	});

	test('should check authRequest subscription action ', () => {
		const data = JSON.parse(response.body);
		const expectedDispatchResult = {
			[AUTH_LOGIN_RESPONSE]: data
		};

		processAuthRequestSubscription({dispatch}, response);
		expect(dispatchResult).toStrictEqual(expectedDispatchResult);
		reset();
	});
});
