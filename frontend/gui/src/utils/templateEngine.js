import { isString } from "./isString";

//< add builder 1. regexp, 2. etractor == extractArgumentBorders, 3. filter : substring to param name
class TemplateEngine {

	//<
	// constructor(extractor, regexp){
	// 	this._extractor = extractor !== undefined ? extractor : extractArgumentBorders;
	// 	this._regexp = regexp !== undefined ? regexp : /{(\s*|[A-Za-z_])\w*\s*}/;
	// }
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
