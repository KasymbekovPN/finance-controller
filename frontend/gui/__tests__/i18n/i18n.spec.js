/**
 * @jest-environment jsdom
 */

import { toNumber } from "@vue/shared";
import I18n from "../../src/i18n/i18n";
import TranslationChuck from "../../src/i18n/trTemplates.js";

const code = 'tr.code';

describe('i18n.js', () => {

	test('should check wrong attempt of creation', () => {
		const expectedError = new TypeError('Cannot set property locale of #<I18n>');

		const f = () => {new I18n()};
		expect(f).toThrow(expectedError);
	});

	test('should check enrichment if chuck-arg has wrong type', () => {
		const expectedError = new TypeError('Cannot enrich #<I18n>');

		const i18n = new I18n('en');
		const f = () => {i18n.enrich()};
		expect(f).toThrow(expectedError);
	});

	test('should check translation if code-arg has wrong type', () => {
		const expectedError = new TypeError('Cannot translate #<I18n> - code has wrong type');

		const i18n = new I18n('en');
		const f = () => {i18n.translate()};
		expect(f).toThrow(expectedError);
	});

	test('should check translation if code-arg is absent', () => {
		const i18n = new I18n('en');

		expect(i18n.translate(code)).toBe(code);
	});

	test('should check translation if default locale is used', () => {
		const trChuck = new TranslationChuck(code)
			.enrich('en', 'tr-en')
			.enrich('ru', 'tr-ru');

		const i18n = new I18n('en')
			.enrich(trChuck);

		expect(i18n.translate(code)).toBe('tr-en');
	});

	test('should check translation', () => {
		const trChuck = new TranslationChuck(code)
			.enrich('en', 'tr-en')
			.enrich('ru', 'tr-ru');

		const i18n = new I18n('en')
			.enrich(trChuck);

		expect(i18n.translate(code, 'en')).toBe('tr-en');
		expect(i18n.translate(code, 'ru')).toBe('tr-ru');
	});
});
