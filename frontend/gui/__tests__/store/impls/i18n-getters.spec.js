import { getTranslation } from "../../../src/store/imps/i18n-getters";

describe('i18n-getters.js', () => {

	const code = 'code';
	const args = {
		key0: 'value0',
		key1: 'value1'
	};

	class TestI18n {
		getTemplate(code, locale) {
			return '__{key0}__{key1}__';
		}
	}

	test('should check getTranslation-getter if locale is not string', () => {
		const state = {};
		const result = getTranslation(state, code, args);

		expect(result).toBe(code);
	});

	test('should check getTranslation-getter if i18n is undefined', () => {
		const state = {
			locale: 'en'
		};
		const result = getTranslation(state, code, args);

		expect(result).toBe(code);
	});

	test('should check getTranslation-getter', () => {
		const expectedResult = '__value0__value1__';
		const state = {
			locale: 'en',
			i18n: new TestI18n()
		};

		const result = getTranslation(state, code, args);
		expect(result).toBe(expectedResult);
	});
});
