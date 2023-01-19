import {
	doOnConnection,
	doOnDisconnection,
	doOnSending,
	actOnConnectionCreation
} from "../../../src/store/imps/connection-actions";
import {
	CONNECTION_CONNECT,
	CONNECTION_CREATE,
	CONNECTION_DISCONNECT,
	CONNECTION_SEND
} from "../../../src/store/sconst/connection";

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
			command: CONNECTION_CREATE,
			data: {connection, sessionId}
		};
		const expectedDispatchResult = {command: CONNECTION_CONNECT};

		actOnConnectionCreation(commit, dispatch, connection, sessionId);
		expect(commitResult).toStrictEqual(expectedCommitResult);
		expect(dispatchResult).toStrictEqual(expectedDispatchResult);
		reset();
	});

	test('should check doOnConnection-actions', () => {
		const expectedCommitResult = {command: CONNECTION_CONNECT};

		doOnConnection({commit});
		expect(commitResult).toStrictEqual(expectedCommitResult);
		reset();
	});

	test('should check doOnDisconnection-actions', () => {
		const expectedCommitResult = {command: CONNECTION_DISCONNECT};

		doOnDisconnection({commit});
		expect(commitResult).toStrictEqual(expectedCommitResult);
		reset();
	});

	test('should check doSending-actions', () => {
		const destination = 'some destination';
		const headers = {};
		const body = {};
		const expectedCommitResult = {
			command: CONNECTION_SEND,
			data: {destination, headers, body}
		};

		doOnSending({commit}, {destination, headers, body});
		expect(commitResult).toStrictEqual(expectedCommitResult);
		reset();
	});
});
