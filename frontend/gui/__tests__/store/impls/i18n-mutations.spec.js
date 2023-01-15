import I18n from "../../../src/i18n/i18n";
import { mutateOnSetLocale, mutateOnSetTemplates } from "../../../src/store/imps/i18n-mutations";

describe('i18n-mutations.js', () => {

	test('should check mutateOnSetLocale', () => {
		const locale = 'locale';
		const expectedState = {locale}

		let state = {};
		mutateOnSetLocale(state, locale);
		expect(state).toStrictEqual(expectedState);
	});

	test('should check mutateOnSetTemplates', () => {
		const data = {
			templates : {
				'some.code' : {'en' : 'enTr', 'ru': 'ruTr'}
			}
		};

		let state = {};
		mutateOnSetTemplates(state, data);

		expect(state.i18n instanceof I18n).toBe(true);
		expect(state.i18n.getTemplate('some.code', 'en')).toBe('enTr');
		expect(state.i18n.getTemplate('some.code', 'ru')).toBe('ruTr');
	});
});
