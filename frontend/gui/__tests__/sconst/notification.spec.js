import each from "jest-each";
import {
	getIcon,
	NOTIFICATION
} from "../../src/sconst/notification";


describe('notification.js', () => {
	each([
		[undefined, 'info'],
		[NOTIFICATION.INFO, 'info'],
		[NOTIFICATION.WARNING, 'warning'],
		[NOTIFICATION.ERROR, 'error']
	]).it('when level is %s getIcon must return %s', (level, expected) => {
		expect(getIcon(level)).toBe(expected);
	})
});
