
class TemplateEngine {

}

class Borders {

	constructor(begin, end) {
		this._begin = begin;
		this._end = end;

		this._isValid = Number.isInteger(this._begin) &&
						Number.isInteger(this._end) &&
						this._begin >= 0 &&
						this._end > this._begin;
	}

	get begin(){
		return this._begin;
	}

	get end(){
		return this._end;
	}

	get isValid() {
		return this._isValid;
	}
}

function extractArgumentBorders(regexp, line){
	let result = [];
	let match;
	while((match = regexp.exec(line)) !== null){
		result.push(new Borders(match.index, match.index + match[0].length));
	}

	return result;
}

export {
	TemplateEngine,
	Borders,
	extractArgumentBorders
};
