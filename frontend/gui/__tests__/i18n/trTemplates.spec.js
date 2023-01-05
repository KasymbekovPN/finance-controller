import {
	TrTemplates,
	createTrTemplates
} from "../../src/i18n/trTemplates.js";

const code = 'tr.code';
const ruLocale = 'ru';
const enLocale = 'en';
const wrongLocale = 'wrong';
const ruTranslation = 'ru translation';
const enTranslation = 'en translation';
const templates = {
	[ruLocale]: ruTranslation,
	[enLocale]: enTranslation
};

describe('templateChunk.js', () => {

	test('should check TrTemplate code getting', () => {
		const trTemplates = new TrTemplates(code, {});
		expect(trTemplates.code).toBe(code);
	});

	test('should check TrTemplate translation', () => {
		const trTemplates = new TrTemplates(code, templates);
		expect(trTemplates.translate(ruLocale)).toBe(templates[ruLocale]);
		expect(trTemplates.translate(enLocale)).toBe(templates[enLocale]);
		expect(trTemplates.translate(wrongLocale)).toBeUndefined();
	});

	test('should check TrTemplate creation if code-arg is undefined', () => {
		const expectedResult = {success: false, code: 'tr-templates.creation.code.not-string'};

		const result = createTrTemplates();
		expect(result).toStrictEqual(expectedResult);
	});

	test('should check TrTemplate creation if code-arg is not string', () => {
		const expectedResult = {success: false, code: 'tr-templates.creation.code.not-string'};

		const result = createTrTemplates(123);
		expect(result).toStrictEqual(expectedResult);
	});

	test('should check TrTemplate creation if templates-arg is not object', () => {
		const expectedResult = {success: false, code: 'tr-templates.creation.templates.not-object'};

		const result = createTrTemplates(code);
		expect(result).toStrictEqual(expectedResult);
	});

	test('should check TrTemplate creation if templates-arg is empty', () => {
		const expectedResult = {success: false, code: 'tr-templates.creation.templates.empty'};

		const result = createTrTemplates(code, {});
		expect(result).toStrictEqual(expectedResult);
	});

	test('should check TrTemplate creation if templates-arg contains non-string fields only', () => {
		const expectedResult = {success: false, code: 'tr-templates.creation.templates.empty'};

		const result = createTrTemplates(code, {
			[ruLocale]: 1,
			[enLocale]: 2
		});
		expect(result).toStrictEqual(expectedResult);
	});

	test('should check TrTemplate creation if templates-arg contains non-string fields', () => {
		const expectedResult = {success: true, value: {[ruLocale]: ruTranslation}};

		const result = createTrTemplates(code, {
			[ruLocale]: ruTranslation,
			[enLocale]: 2
		});
		expect(result).toStrictEqual(expectedResult);
	});

	test('should check TrTemplate creation if templates-arg contains string fields only', () => {
		const expectedResult = {success: true, value: new TrTemplates(code, templates)};

		const result = createTrTemplates(code, templates);
		expect(result).toStrictEqual(expectedResult);
	});
});
