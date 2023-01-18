class Connection {

	constructor(client){
		this._client = client;
	}

	set openCallback(callback){
		this._openCallback = callback;
	}

	set errorCallback(callback){
		this._errorCallback = callback;
	}

	set closeCallback(callback) {
		this._closeCallback = callback;
	}

	set subscriptionCallback(callback){
		this._subscriptionCallback = callback;
	}

	set subscriptions(subscriptions){
		this._subscriptions = subscriptions;
	}

	get connected(){
		return this._connected;
	}

	//<
	// addSubscription(path, callback){
	// 	this._subscription.set(path, callback);
	// 	return this;
	// }

	connect(sessionId, headers) {
		const runCallback = (callback) => {
			if (callback !== undefined){ callback(); }
		};

		this._client.connect(
			headers !== undefined ? headers : {},
			(/*frame*/) => {
				this._connected = true;
				//<
				// if (this._subscriptionCallback !== undefined){
				// 	for (const action in this._subscriptions) {
				// 		this._client.subscribe(
				// 			`${this._subscriptions[action]}${sessionId}`,
				// 			this._subscriptionCallback
				// 		);
				// 	}
				// }
				// runCallback(this._openCallback);
			},
			(/*error*/) => {
				//<
				// this._connected = false;
				// runCallback(this._errorCallback);
			},
			(/*closeEvent*/) => {
				///<
				// this._connected = false;
				// runCallback(this._closeCallback);
			}
		);
	}

	disconnect(){
		this._connected = false;
		this._client.disconnect();
	}

	send (destination, headers, body){
		if (this._connected){
			this._client.send(destination, headers, body);
		}
	}
}

export {
	Connection
};



//< del
// class Connection {

// 	// constructor(clientCreator, headers){
// 	// 	this._connected = false;
// 	// 	this._subscription = new Map();
// 	// 	this._client = clientCreator();
// 	// 	this._headers = headers;
// 	// }
// 	//<
// 	constructor(client, headers){
// 		this._connected = false;
// 		this._subscription = new Map();
// 		this._client = client;
// 		this._headers = headers;
// 	}

// 	set openCallback(f){
// 		this._openCallback = f;
// 	}

// 	set errorCallback(f){
// 		this._errorCallback = f;
// 	}

// 	set closeCallback(f){
// 		this._closeCallback = f;
// 	}

// 	get connected(){
// 		return this._connected;
// 	}

// 	addSubscription(path, callback){
// 		this._subscription.set(path, callback);
// 		return this;
// 	}

// 	//< args: sessionId, ?headers
// 	connect() {
// 		const runCallback = (callback) => {
// 			if (callback !== undefined){
// 				callback();
// 			}
// 		};

// 		this._client.connect(
// 			this._headers,
// 			(/*frame*/) => {
// 				this._connected = true;
// 				this._subscription.forEach((value, key) => {
// 					this._client.subscribe(key, value);
// 				});
// 				runCallback(this._openCallback);
// 			},
// 			(/*error*/) => {
// 				this._connected = false;
// 				runCallback(this._errorCallback);
// 			},
// 			(/*closeEvent*/) => {
// 				this._connected = false;
// 				runCallback(this._closeCallback);
// 			}
// 		);
// 	}

// 	disconnect(){
// 		this._connected = false;
// 		this._client.disconnect();
// 	}

// 	send (destination, headers, body){
// 		if (this._connected){
// 			this._client.send(destination, headers, body);
// 		}
// 	}
// }

// export {
// 	Connection
// };
