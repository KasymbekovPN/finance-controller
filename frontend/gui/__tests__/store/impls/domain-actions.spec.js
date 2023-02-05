import config from "../../../config";
import { CONNECTION } from "../../../src/sconst/connection";
import {
	processTagCreationRequest,
	processTagDeletingRequest,
	processTagUpdatingRequest
 } from "../../../src/store/imps/domain-actions";

describe('domain-actions.js', () => {

	const id = 123;
	const name = 'tag.name';

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
				body: {name}
			}
		};

		processTagCreationRequest({dispatch}, {name});
		expect(dispatchResult).toStrictEqual(expectedDispatchResult);
		reset();
	});

	test('should check action on tag updating', () => {
		const expectedDispatchResult = {
			command: CONNECTION.SEND,
			data: {
				destination: config.requests.tag.update,
				headers: {},
				body: {id, name}
			}
		};

		processTagUpdatingRequest({dispatch}, {id, name});
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

		processTagDeletingRequest({dispatch}, {id});
		expect(dispatchResult).toStrictEqual(expectedDispatchResult);
		reset();
	});
});
