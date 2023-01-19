import { Connection } from "../../src/connection/connection.js";

describe('connection.js', () => {

	const sessionId = 'sessionId';

	class TestSuccessClient {
		connect(headers, successCallback, errorCallback, closeCallback) {
			successCallback();
		}

		subscribe(destination, callback) {
			this.destination = destination;
			this.callback = callback;
		}
	}

	class TestErrorClient {
		connect(headers, successCallback, errorCallback, closeCallback) {
			errorCallback();
		}
	}

	class TestCloseClient {
		connect(headers, successCallback, errorCallback, closeCallback) {
			closeCallback();
		}
	}

	class TestDisconnectionClient{
		disconnect(){
			this.disconnected = true;
		}
	}

	class TestSendClient{
		connect(headers, successCallback, errorCallback, closeCallback){
			successCallback();
		}
		send (destination, headers, body){
			this.data = {destination, headers, body};
		}
	}

	test('should check connect-method if open without callbacks', () => {
		const connection = new Connection(new TestSuccessClient());
		connection.connect(sessionId);

		expect(connection.connected).toBe(true);
	});

	test('should check connect-method if open with subscriptionCallback', () => {
		const client = new TestSuccessClient();
		const subscriptionCallback = response => {};
		const subscriptions = {action: 'dest/in/ation/'};
		const expectedDestination = `${subscriptions['action']}${sessionId}`;
		const expectedCallback = subscriptionCallback;

		const connection = new Connection(client);
		connection.subscriptions = subscriptions;
		connection.subscriptionCallback = subscriptionCallback;
		connection.connect(sessionId);

		expect(connection.connected).toBe(true);
		expect(client.destination).toBe(expectedDestination);
		expect(client.callback).toBe(expectedCallback);
	});

	test('should check connect-method if open with openCallback', () => {
		const client = new TestSuccessClient();
		let callbackCalled = false;
		const callback = () => { callbackCalled = true; };

		const connection = new Connection(client);
		connection.openCallback = callback;
		connection.connect(sessionId);

		expect(connection.connected).toBe(true);
		expect(callbackCalled).toBe(true);
	});

	test('should check connect-method if open', () => {
		const client = new TestSuccessClient();
		let callbackCalled = false;
		const callback = () => { callbackCalled = true; };
		const subscriptionCallback = response => {};
		const subscriptions = {action: 'dest/in/ation/'};
		const expectedDestination = `${subscriptions['action']}${sessionId}`;
		const expectedCallback = subscriptionCallback;

		const connection = new Connection(client);
		connection.subscriptions = subscriptions;
		connection.subscriptionCallback = subscriptionCallback;
		connection.openCallback = callback;
		connection.connect(sessionId);

		expect(connection.connected).toBe(true);
		expect(client.destination).toBe(expectedDestination);
		expect(client.callback).toBe(expectedCallback);
		expect(callbackCalled).toBe(true);
	});

	test('should check connect-method if error without callback', () => {
		const client = new TestErrorClient();

		const connection = new Connection(client);
		connection.connect(sessionId);

		expect(connection.connected).toBe(false);
	});

	test('should check connect-method if error', () => {
		const client = new TestErrorClient();
		let callbackCalled = false;
		const callback = () => { callbackCalled = true; };

		const connection = new Connection(client);
		connection.errorCallback = callback;
		connection.connect(sessionId);

		expect(connection.connected).toBe(false);
		expect(callbackCalled).toBe(true);
	});

	test('should check connect-method if close without callback', () => {
		const client = new TestCloseClient();

		const connection = new Connection(client);
		connection.connect(sessionId);

		expect(connection.connected).toBe(false);
	});

	test('should check connect-method if close', () => {
		const client = new TestCloseClient();
		let callbackCalled = false;
		const callback = () => { callbackCalled = true; };

		const connection = new Connection(client);
		connection.closeCallback = callback;
		connection.connect(sessionId);

		expect(connection.connected).toBe(false);
		expect(callbackCalled).toBe(true);
	});

	test('should check disconnection', () => {
		const client = new TestDisconnectionClient();

		const connection = new Connection(client);
		connection.disconnect();

		expect(client.disconnected).toBe(true);
		expect(connection.connected).toBe(false);
	});

	test('should check sending if disconnected', () => {
		const destination = 'dest/ina/tion';
		const headers = {arg: 'value'}
		const body = 'body';
		const client = new TestSendClient();

		const connection = new Connection(client);
		connection.send(destination, headers, body);

		expect(client.data).toStrictEqual(undefined);
	});

	test('should check sending if connected', () => {
		const destination = 'dest/ina/tion';
		const headers = {arg: 'value'}
		const body = 'body';
		const expectedData = {destination, headers, body};
		const client = new TestSendClient();

		const connection = new Connection(client);
		connection.connect(sessionId);
		connection.send(destination, headers, body);

		expect(client.data).toStrictEqual(expectedData);
	});
});
