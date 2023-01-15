import { I18N_SET_LOCALE, I18N_SET_TEMPLATES } from "../../../src/store/sconst/i18n";
import { setI18nLocale, setI18nTemplates } from "../../../src/store/imps/i18n-actions";

describe('i18n-actions.js', () => {

	let commitResult;
	const commit = (command, data) => {
		commitResult = { command, data };
	};

	const reset = () => {
		commitResult = undefined;
	};

	test('should check setI18nLocale-action', () => {
		const locale = 'en';
		const expectedResult = {
			command: I18N_SET_LOCALE,
			data: locale
		};

		setI18nLocale({commit}, locale);
		expect(commitResult).toStrictEqual(expectedResult);
		reset();
	});
	test('should check setI18nTemplates-action', () => {
		const data = {value: 'value'};
		const expectedResult = {
			command: I18N_SET_TEMPLATES,
			data
		};

		setI18nTemplates({commit}, data);
		expect(commitResult).toStrictEqual(expectedResult);
		reset();
	});
});
