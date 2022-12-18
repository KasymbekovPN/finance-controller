import { isString } from "../utils/isString";

class TrTemplates {

	constructor(code, templates){
		this._code = code;
		this._templates = templates;
	}

	get code(){
		return this._code;
	}

	getTemplate(locale){
		return this._templates.has(locale) ? this._templates.get(locale) : undefined;
	}
}

class TrTemplatesBuilder {

	constructor(){
		this._templates = new Map();
	}

	code(code){
		this._code = code;
		return this;
	}

	template(locale, template){
		this._templates.set(locale, template);
		return this;
	}

	build() {
		checkCode(this._code);
		checkTemplates(this._templates);
		return new TrTemplates(this._code, this._templates);
	}
}

function checkCode(code){
	if (!isString(code)){
		throw new TypeError('#<TrTemplatesBuilder> Cannot build #<TrTemplates> because code is not string');
	}
}

function checkTemplates(templates){
	if (templates.size == 0){
		throw new TypeError('#<TrTemplatesBuilder> Cannot build #<TrTemplates> because templates is empty');
	}

	templates.forEach((value, key) => {
		if (!isString(key)){
			throw new TypeError('#<TrTemplatesBuilder> Cannot build #<TrTemplates> because locale is not string');
		}
		if (!isString(value)){
			throw new TypeError('#<TrTemplatesBuilder> Cannot build #<TrTemplates> because template is not string');
		}
	});
}

export {
	TrTemplates,
	TrTemplatesBuilder
};
