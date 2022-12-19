/**
 * @jest-environment jsdom
 */

import { I18nBuilder } from "../../src/i18n/i18n";
import { TrTemplatesBuilder } from "../../src/i18n/trTemplates.js";

const code = 'tr.code';
const ansentCode = 'absent.code';

describe('i18n.js', () => {

	test('should check building if locale is undefined', () => {
		const expectedError = new TypeError('#<I18nBuilder> Cannot build #<I18n> because locale is not string');

		const f = () => {new I18nBuilder().build();};
		expect(f).toThrow(expectedError);
	});

	test('should check building if locale is not string', () => {
		const expectedError = new TypeError('#<I18nBuilder> Cannot build #<I18n> because locale is not string');

		const f = () => {
			new I18nBuilder()
				.locale(123)
				.build();
		};
		expect(f).toThrow(expectedError);
	});

	test('should check building if template is not TrTemplates', () => {
		const expectedError = new TypeError('#<I18nBuilder> Cannot build #<I18n> because template is not #<TrTemplates>');

		const f = () => {
			new I18nBuilder()
				.locale('en')
				.template(123)
				.build();
		};
		expect(f).toThrow(expectedError);
	});

	test('should check translation if template absent', () => {
		const i18n = new I18nBuilder()
			.locale('en')
			.build();

		const result = i18n.translate(123);
		expect(result).toBe('123');
	});

	test('should check translation', () => {
		const trTemp = new TrTemplatesBuilder()
			.code(code)
			.template('ru', 'tr-ru')
			.build();
		const i18n = new I18nBuilder()
			.locale('en')
			.template(trTemp)
			.build();

		const result = i18n.translate(code, 'ru');
		expect(result).toBe('tr-ru');
	});
});
