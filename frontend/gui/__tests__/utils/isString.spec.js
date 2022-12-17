/**
 * @jest-environment jsdom
 */

import { isString } from "../../src/utils/isString";

describe('isString.js', () => {
	test('should check if arg is undefined', () => {
		expect(isString()).toBe(false);
	});

	test('should check if arg is neither str nor String', () => {
		expect(isString(123)).toBe(false);
	});

	test('should check if arg is str', () => {
		expect(isString('123')).toBe(true);
	});

	test('should check if arg is String', () => {
		expect(isString(new String('123'))).toBe(true);
	});
});
