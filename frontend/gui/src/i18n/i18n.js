import { isString } from "../utils/isString";
import { TrTemplates } from "./trTemplates";

class I18n {

	constructor(){
		this._templates = new Map();
	}

	addTemplate(template){
		if (template instanceof TrTemplates){
			this._templates.set(template.code, template);
		}
		return this;
	}

	getTemplate(code, locale) {
		let template = undefined;
		if (isString(code) && isString(locale) && this._templates.has(code)){
			template = this._templates.get(code).translate(locale);
		}

		return template !== undefined ? template : '' + code;
	}
}

export default I18n;
