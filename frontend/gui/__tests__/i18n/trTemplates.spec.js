/**
 * @jest-environment jsdom
 */

import { TrTemplatesBuilder } from "../../src/i18n/trTemplates.js";

const code = 'tr.code';

describe('templateChunk.js', () => {

	test('should check building if code is undefined', () => {
		const expectedError = new TypeError('#<TrTemplatesBuilder> Cannot build #<TrTemplates> because code is not string');

		const f = () => {new TrTemplatesBuilder().build();};
		expect(f).toThrow(expectedError);
	});

	test('should check building if code is not string', () => {
		const expectedError = new TypeError('#<TrTemplatesBuilder> Cannot build #<TrTemplates> because code is not string');

		const f = () => {
			new TrTemplatesBuilder()
				.code(123)
				.build();
		};
		expect(f).toThrow(expectedError);
	});

	test('should check building if templates is empty', () => {
		const expectedError = new TypeError('#<TrTemplatesBuilder> Cannot build #<TrTemplates> because templates is empty');

		const f = () => {
			new TrTemplatesBuilder()
				.code(code)
				.build();
		};
		expect(f).toThrow(expectedError);
	});

	test('should check building if locale is undefined', () => {
		const expectedError = new TypeError('#<TrTemplatesBuilder> Cannot build #<TrTemplates> because locale is not string');

		const f = () => {
			new TrTemplatesBuilder()
				.code(code)
				.template(undefined)
				.template(undefined)
				.build();
		};
		expect(f).toThrow(expectedError);
	});

	test('should check building if locale is not string', () => {
		const expectedError = new TypeError('#<TrTemplatesBuilder> Cannot build #<TrTemplates> because locale is not string');

		const f = () => {
			new TrTemplatesBuilder()
				.code(code)
				.template(123)
				.template(456)
				.build();
		};
		expect(f).toThrow(expectedError);
	});

	test('should check building if template is undefined', () => {
		const expectedError = new TypeError('#<TrTemplatesBuilder> Cannot build #<TrTemplates> because template is not string');

		const f = () => {
			new TrTemplatesBuilder()
				.code(code)
				.template('ru')
				.template('en')
				.build();
		};
		expect(f).toThrow(expectedError);
	});

	test('should check building if template is not string', () => {
		const expectedError = new TypeError('#<TrTemplatesBuilder> Cannot build #<TrTemplates> because template is not string');

		const f = () => {
			new TrTemplatesBuilder()
				.code(code)
				.template('ru', 123)
				.template('en', 456)
				.build();
		};
		expect(f).toThrow(expectedError);
	});

	test('should check code getting', () => {
		const trChuck = new TrTemplatesBuilder()
			.code(code)
			.template('ru', 'tr-ru')
			.template('en', 'tr-en')
			.build();
		expect(trChuck.code).toBe(code);
	});

	test('should check template getting if it is not enriched', () => {
		const trChuck = new TrTemplatesBuilder()
			.code(code)
			.template('ru', 'tr-ru')
			.build();
		expect(trChuck.getTemplate('en')).toBe(undefined);
	});

	test('should check template getting', () => {
		const trChuck = new TrTemplatesBuilder()
			.code(code)
			.template('ru', 'tr-ru')
			.template('en', 'tr-en')
			.build();
		expect(trChuck.getTemplate('en')).toBe('tr-en');
	});
});
