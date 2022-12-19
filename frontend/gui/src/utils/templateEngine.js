
class TemplateEngine {

}

class Borders {

	constructor(begin, end) {
		this._begin = begin;
		this._end = end;

		//< add isInt.js
		// function isInt(value) {
		// 	return !isNaN(value) &&
		// 		   parseInt(Number(value)) == value &&
		// 		   !isNaN(parseInt(value, 10));
		//   }

		// this._isValid = true &
		// this._begin >= 0 & this._end > this._begin;
		//<
		if (begin < 0){
			this._isValid = false;
		}
		if (end <= begin){
			this._isValid = false;
		}
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

}

export {
	TemplateEngine,
	Borders,
	extractArgumentBorders
};
