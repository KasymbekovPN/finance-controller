import { TrTemplates } from "../../src/i18n/trTemplates";
import I18n from "../../src/i18n/i18n";

const code = 'tr.code';

describe('i18n.js', () => {

	test('should check template addition if some one is not TrTemplates', () => {
		const tr0 = new TrTemplates('code0', {ru: 'ru0'});
		const expected = new Map();
		expected.set(tr0.code, tr0);

		const i18n = new I18n()
			.addTemplate(tr0)
			.addTemplate("wrong arg");

		expect(i18n._templates).toStrictEqual(expected);
	});

	test('should check template addition', () => {
		const tr0 = new TrTemplates('code0', {ru: 'ru0'});
		const tr1 = new TrTemplates('code1', {ru: 'ru1'});
		const expected = new Map();
		expected.set(tr0.code, tr0);
		expected.set(tr1.code, tr1);

		const i18n = new I18n()
			.addTemplate(tr0)
			.addTemplate(tr1);

		expect(i18n._templates).toStrictEqual(expected);
	});

	test('should check translation is code is not string', () => {
		const result = new I18n().getTemplate(123);
		expect(result).toBe('123');
	});

	test('should check translation is locale is not string', () => {
		const result = new I18n().getTemplate(code, 123);
		expect(result).toBe(code);
	});

	test('should check translation is templates by code is absent', () => {
		const result = new I18n().getTemplate(code);
		expect(result).toBe(code);
	});

	test('should check translation is templates by code does not contain locale', () => {
		const i18n = new I18n();
		i18n.addTemplate(new TrTemplates(code, {ru: 'ru translation'}));

		const result = i18n.getTemplate(code, 'en');
		expect(result).toBe(code);
	});

	test('should check translation', () => {
		const i18n = new I18n();
		i18n.addTemplate(new TrTemplates(code, {ru: 'ru translation', en: 'en translation'}));

		const result = i18n.getTemplate(code, 'en');
		expect(result).toBe('en translation');
	});
});
