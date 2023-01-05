import { isString } from "../utils/isString";

class TrTemplates {

	constructor(code, templates){
		this._code = code;
		this._templates = templates;
	}

	get code(){
		return this._code;
	}

	translate(locale){
		return this._templates[locale];
	}
}

function createTrTemplates(code, templates){
	const createFailResult = code => {return {success: false, code: `tr-templates.creation.${code}`}};
	const createResult = (code, templates) => {return {success: true,  value: new TrTemplates(code, templates)}};

	if (!isString(code)){
		return createFailResult('code.not-string');
	}

	if (typeof templates !== 'object'){
		return createFailResult('templates.not-object');
	}

	let ts = {};
	for (const key in templates){
		if (isString(templates[key])){
			ts[key] = templates[key];
		}
	}

	return Object.keys(ts).length === 0 ? createFailResult('templates.empty') : createResult(code, ts);
}

export {
	TrTemplates,
	createTrTemplates
};
