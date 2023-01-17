import {
	mutateOnConnection,
	mutateOnConnectionCreation,
	mutateOnDisconnection,
	mutateOnSending
} from "../../../src/store/imps/connection-mutations";

describe('connection-mutations.js', () => {

	test('should check  mutateOnConnectionCreation mutation', () => {
		const connection = {data: 'value'};
		const sessionId = 'sessionId';
		const expectedState = { connection, sessionId };

		let state = {};
		mutateOnConnectionCreation(state, {connection, sessionId});
		expect(state).toStrictEqual(expectedState);
	});

	test('should check  mutateOnConnection mutation', () => {
		class TestConnection {
			connect() { this.connectCalled = true; }
		}

		let state = { connection: new TestConnection() };
		mutateOnConnection(state);
		expect(state.connection.connectCalled).toBe(true);
	});

	test('should check  mutateOnDisconnection mutation', () => {
		class TestConnection {
			disconnect() { this.disconnectCalled = true; }
		}

		let state = { connection: new TestConnection() };
		mutateOnDisconnection(state);
		expect(state.connection.disconnectCalled).toBe(true);
	});

	test('should check  mutateOnSending mutation', () => {
		class TestConnection {
			send(destination, headers, body) {
				this.result = { destination, headers, body};
			}
		};

		const destination = '/destination';
		const sessionId = 'sessionId';
		const headers = { attr: 'value'};
		const body = {data: 'value'};
		const expectedResult = {
			destination: `/app${destination}/${sessionId}`,
			headers,
			body: JSON.stringify(body)
		};

		let state = {
			connection: new TestConnection(),
			sessionId
		};
		mutateOnSending(state, {destination, headers, body});
		expect(state.connection.result).toStrictEqual(expectedResult);
	});
});
