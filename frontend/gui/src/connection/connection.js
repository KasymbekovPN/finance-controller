class Connection {

	constructor(clientCreator, headers){
		this._connected = false;
		this._subscription = new Map();
		this._client = clientCreator();
		this._headers = headers;
	}

	set openCallback(f){
		this._openCallback = f;
	}

	set errorCallback(f){
		this._errorCallback = f;
	}

	set closeCallback(f){
		this._closeCallback = f;
	}

	get connected(){
		return this._connected;
	}

	addSubscription(path, callback){
		this._subscription.set(path, callback);
		return this;
	}

	connect() {
		const runCallback = (callback) => {
			if (callback !== undefined){
				callback();
			}
		};

		this._client.connect(
			this._headers,
			(/*frame*/) => {
				this._connected = true;
				this._subscription.forEach((value, key) => {
					this._client.subscribe(key, value);
				});
				runCallback(this._openCallback);
			},
			(/*error*/) => {
				this._connected = false;
				runCallback(this._errorCallback);
			},
			(/*closeEvent*/) => {
				this._connected = false;
				runCallback(this._closeCallback);
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
