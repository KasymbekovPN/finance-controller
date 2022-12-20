import {
	Borders,
	extractArgumentBorders
} from "../../src/utils/templateEngine";

const reg = /ax/g;

describe('templateEngine.js', () => {

	test('should check Borders if init begin < 0', () => {
		const borders = new Borders(-1, 0);
		expect(borders.isValid).toBe(false);
	});

	test('should check Borders if init end <= begin', () => {
		const borders = new Borders(10, 1);
		expect(borders.isValid).toBe(false);
	});

	test('should check Borders', () => {
		const begin = 3;
		const end = 10;
		const borders = new Borders(begin, end);
		expect(borders.isValid).toBe(true);
		expect(borders.begin).toBe(begin);
		expect(borders.end).toBe(end);
	});

	test('should check argument extraction if subscring occurrencein string is absent', () => {
		const line = 'b b b b b';
		const result = extractArgumentBorders(reg, line);
		expect(result).toStrictEqual([]);
	});

	test('should check argument extraction', () => {
		const expectedResult = [
			new Borders(0,2),
			new Borders(5,7)
		];

		const line = 'axbabaxb';
		const result = extractArgumentBorders(reg, line);
		expect(result).toStrictEqual(expectedResult);
	});
})
