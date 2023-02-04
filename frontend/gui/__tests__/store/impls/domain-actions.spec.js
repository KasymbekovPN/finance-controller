import config from "../../../config";
import { CONNECTION } from "../../../src/sconst/connection";
import {
	actOnTagCreation,
	actOnTagDeleting,
	actOnTagUpdating
 } from "../../../src/store/imps/domain-actions";

describe('domain-actions.js', () => {

	const id = 123;
	const username = 'username';

	let dispatchResult;
	const dispatch = (command, data) => {
		dispatchResult = data !== undefined ? { command, data } : { command };
	};

	const reset = () => {
		dispatchResult = undefined;
	};

	test('should check action on tag creation', () => {
		const expectedDispatchResult = {
			command: CONNECTION.SEND,
			data: {
				destination: config.requests.tag.create,
				headers: {},
				body: {username}
			}
		};

		actOnTagCreation({dispatch}, {username});
		expect(dispatchResult).toStrictEqual(expectedDispatchResult);
		reset();
	});

	test('should check action on tag updating', () => {
		const expectedDispatchResult = {
			command: CONNECTION.SEND,
			data: {
				destination: config.requests.tag.update,
				headers: {},
				body: {id, username}
			}
		};

		actOnTagUpdating({dispatch}, {id, username});
		expect(dispatchResult).toStrictEqual(expectedDispatchResult);
		reset();
	});

	test('should check action on tag deleting', () => {
		const expectedDispatchResult = {
			command: CONNECTION.SEND,
			data: {
				destination: config.requests.tag.delete,
				headers: {},
				body: {id}
			}
		};

		actOnTagDeleting({dispatch}, {id});
		expect(dispatchResult).toStrictEqual(expectedDispatchResult);
		reset();
	});
});
