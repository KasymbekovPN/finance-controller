/**
 * @jest-environment jsdom
 */

import TraslationChuck from "../../src/i18n/translationChuck.js";

const code = 'tr.code';

describe('translationChunk.js', () => {
	test('should check wrong attempt of creation', () => {
		const expectedError = new TypeError('Cannot set property code of #<TraslationChuck>');

		const f = () => {new TraslationChuck()};
		expect(f).toThrow(expectedError);
	});

	test('should check code getting', () => {
		const trChuck = new TraslationChuck(code);

		expect(trChuck.code).toBe(code);
	});

	test('should check enrichment if locale type is wrong', () => {
		const trChuck = new TraslationChuck(code);
		const f = () => {trChuck.enrich()};

		const expectedError = new TypeError('Cannot enrich translation because locale-arg is not string');
		expect(f).toThrow(expectedError);
	});

	test('should check enrichment if translation type is wrong', () => {
		const trChuck = new TraslationChuck(code);
		const f = () => {trChuck.enrich('en')};

		const expectedError = new TypeError('Cannot enrich translation because translation-arg is not string');
		expect(f).toThrow(expectedError);
	});

	test('should check translate-method if it is not enriched', () => {
		const trChuck = new TraslationChuck(code);

		expect(trChuck.translate('en')).toBe(code);
	});

	test('should check translate-method', () => {
		const trChuck = new TraslationChuck(code);

		const tr0 = 'tr0';
		trChuck.enrich('en', tr0);
		expect(trChuck.translate('en')).toBe(tr0);

		const tr1 = 'tr1';
		trChuck.enrich('en', tr1);
		expect(trChuck.translate('en')).toBe(tr1);
	});
});
