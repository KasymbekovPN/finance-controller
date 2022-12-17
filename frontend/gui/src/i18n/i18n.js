import { isString } from "../utils/isString";
import TraslationChuck from "./translationChuck";

class I18n {

	constructor(locale){
		if (isString(locale)){
			this._locale = locale;
			this._chunks = new Map();
		} else {
			throw new TypeError('Cannot set property locale of #<I18n>');
		}
	}

	enrich(chuck){
		if (chuck instanceof TraslationChuck){
			this._chunks.set(chuck.code, chuck);
		} else {
			throw new TypeError('Cannot enrich #<I18n>');
		}

		return this;
	}

	translate(code, locale){
		if (isString(code)){
			if (this._chunks.has(code)){
				const l = isString(locale) ? locale : this._locale;
				return this._chunks.get(code).translate(l);
			} else {
				return code;
			}
		}
		throw new TypeError('Cannot translate #<I18n> - code has wrong type');
	}
}

export default I18n;
