import { isString } from "./isString";

class TemplateEngine {

	constructor(regexp, extractor, filter){
		this._regexp = regexp !== undefined ? regexp : /{(\s*|[A-Za-z_])\w*\s*}/g;
		this._extractor = extractor !== undefined ? extractor : extractArgumentSubstrs;
		this._filter = filter !== undefined ? filter : filterArgumentName;
	}

	execute(template, args){
		const substrs = this._extractor(this._regexp, template);
		const replacingData = new Map();
		for (const substr of substrs){
			if (substr.valid){
				const name = this._filter(substr.value);
				if (!replacingData.has(name)){
					replacingData.set(name, new Set());
				}
				replacingData.get(name).add(substr);
			}
		}

		let result = template;
		replacingData.forEach((value, key) => {
			const arg = args[key];
			if (arg !== undefined) {
				for (const substr of value)	{
					if (substr.valid){
						result = result.replace(substr.value, arg);
					}
				}
			}
		});

		return result;
	}
}

class Substr {

	constructor(value, begin) {
		if (isString(value) && value.length > 0 && Number.isInteger(begin) && begin >= 0){
			this._value = value;
			this._begin = begin;
			this._end = begin + value.length;
			this._valid = true;
		} else {
			this._valid = false;
		}
	}

	get value(){
		return this._value;
	}

	get begin(){
		return this._begin;
	}

	get end(){
		return this._end;
	}

	get valid() {
		return this._valid;
	}
}

function extractArgumentSubstrs(regexp, line){
	let result = [];
	let match;
	while((match = regexp.exec(line)) !== null){
		result.push(new Substr(match[0], match.index));
	}

	return result;
}

function filterArgumentName(rawArgumentName){
	return rawArgumentName
		.replace('{', '')
		.replace('}', '')
		.trim();
}

export {
	TemplateEngine,
	Substr,
	extractArgumentSubstrs,
	filterArgumentName
};
