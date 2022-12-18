import { isString } from "../utils/isString";
import { TrTemplates } from "./trTemplates";

class I18n {

	constructor(locale, templates){
		this._locale = locale;
		this._templates = templates;
	}

	translate(code, locale){
		if (isString(code)){
			if (this._templates.has(code)){
				const l = isString(locale) ? locale : this._locale;
				const template = this._templates.get(code).getTemplate(l);
				if (template != undefined){
					return template;
				}
			}
		}
		return '' + code;
	}
}

class I18nBuilder {

	constructor() {
		this._templates = [];
	}

	locale(locale) {
		this._locale = locale;
		return this;
	}

	template(template){
		this._templates.push(template);
		return this;
	}

	build() {
		checkLocale(this._locale);
		const preparedTemplates = prepareAndCheckTemplates(this._templates);
		return new I18n(this._locale, preparedTemplates);
	}
}

function checkLocale(locale){
	if (!isString(locale)){
		throw new TypeError('#<I18nBuilder> Cannot build #<I18n> because locale is not string');
	}
}

function prepareAndCheckTemplates(list){
	const map = new Map();
	list.forEach(value => {
		if (value instanceof TrTemplates){
			map.set(value.code, value);
		} else {
			throw new TypeError('#<I18nBuilder> Cannot build #<I18n> because template is not #<TrTemplates>');
		}
	});

	return map;
}

export {
	I18n,
	I18nBuilder
};
