import { Borders } from "../../src/utils/templateEngine";

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
})
