import {
	Substr,
	extractArgumentSubstrs,
	filterArgumentName
} from "../../src/utils/templateEngine";

const reg = /ax/g;

describe('templateEngine.js', () => {

	test('should check Substr if value is not string', () => {
		const substr = new Substr();
		expect(substr.valid).toBe(false);
	});

	test('should check Substr if value is empty', () => {
		const substr = new Substr('');
		expect(substr.valid).toBe(false);
	});

	test('should check Substr if begin is not integer', () => {
		const substr = new Substr('value');
		expect(substr.valid).toBe(false);
	});

	test('should check Substr if begin less zero', () => {
		const substr = new Substr('value', -1);
		expect(substr.valid).toBe(false);
	});

	test('should check Substr', () => {
		const expectedValue = 'value';
		const expectedBegin = 10;
		const expectedEnd = expectedBegin + expectedValue.length;

		const substr = new Substr(expectedValue, expectedBegin);
		expect(substr.valid).toBe(true);
		expect(substr.value).toBe(expectedValue);
		expect(substr.begin).toBe(expectedBegin);
		expect(substr.end).toBe(expectedEnd);
	});


	test('should check argument extraction if substring occurrence in string is absent', () => {
		const line = 'b b b b b';
		const result = extractArgumentSubstrs(reg, line);
		expect(result).toStrictEqual([]);
	});

	test('should check argument extraction', () => {
		const expectedResult = [
			new Substr('ax', 0),
			new Substr('ax', 5)
		];

		const line = 'axbabaxb';
		const result = extractArgumentSubstrs(reg, line);
		expect(result).toStrictEqual(expectedResult);
	});

	test('should check filtration if string does not contain disallowed chars' , () => {
		const rawValue = 'abc';
		const expectedValue = 'abc';
		const value = filterArgumentName(rawValue);
		expect(value).toBe(expectedValue);
	});

	test('should check filtration if string contains disallowed chars' , () => {
		const rawValue = '{   abc    }';
		const expectedValue = 'abc';
		const value = filterArgumentName(rawValue);
		expect(value).toBe(expectedValue);
	});
})
