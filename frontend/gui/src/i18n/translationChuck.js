import { isString } from "../utils/isString";

class TraslationChuck{

	constructor(code){
		if (isString(code)){
			this._code = code;
			this._translations = new Map();
		} else {
			throw new TypeError('Cannot set property code of #<TraslationChuck> which has only a getter');
		}
	}

	get code(){
		return this._code;
	}

	enrich(locale, translation){
		if (!isString(locale)){
			throw new TypeError('Cannot enrich translation because locale-arg is not string');
		}
		if (!isString(translation)){
			throw new TypeError('Cannot enrich translation because translation-arg is not string');
		}
		this._translations.set(locale, translation);

		return this;
	}

	translate(locale) {
		return this._translations.has(locale)
			? this._translations.get(locale)
			: this._code;
	}
}

export default TraslationChuck;
