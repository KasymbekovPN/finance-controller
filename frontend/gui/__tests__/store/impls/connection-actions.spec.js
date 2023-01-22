import {
	doOnConnection,
	doOnDisconnection,
	doOnSending,
	actOnConnectionCreation
} from "../../../src/store/imps/connection-actions";
import { CONNECTION } from "../../../src/sconst/connection";

describe('connection-actions.js', () => {

	let commitResult;
	const commit = (command, data) => {
		commitResult = data !== undefined ? { command, data } : { command };
	};
	let dispatchResult;
	const dispatch = (command, data) => {
		dispatchResult = data !== undefined ? { command, data } : { command };
	};

	const reset = () => {
		dispatchResult = commitResult = undefined;
	};

	class TestConnection {}

	test('should check actOnConnectionCreation-actions', () => {
		const sessionId = 'sessionId';
		const connection = new TestConnection();
		const expectedCommitResult = {
			command: CONNECTION.CREATE,
			data: {connection, sessionId}
		};
		const expectedDispatchResult = {command: CONNECTION.CONNECT};

		actOnConnectionCreation(commit, dispatch, connection, sessionId);
		expect(commitResult).toStrictEqual(expectedCommitResult);
		expect(dispatchResult).toStrictEqual(expectedDispatchResult);
		reset();
	});

	test('should check doOnConnection-actions', () => {
		const expectedCommitResult = {command: CONNECTION.CONNECT};

		doOnConnection({commit});
		expect(commitResult).toStrictEqual(expectedCommitResult);
		reset();
	});

	test('should check doOnDisconnection-actions', () => {
		const expectedCommitResult = {command: CONNECTION.DISCONNECT};

		doOnDisconnection({commit});
		expect(commitResult).toStrictEqual(expectedCommitResult);
		reset();
	});

	test('should check doSending-actions', () => {
		const destination = 'some destination';
		const headers = {};
		const body = {};
		const expectedCommitResult = {
			command: CONNECTION.SEND,
			data: {destination, headers, body}
		};

		doOnSending({commit}, {destination, headers, body});
		expect(commitResult).toStrictEqual(expectedCommitResult);
		reset();
	});
});
