import { TemplateEngine } from "../../utils/templateEngine";
import { isString } from "../../utils/isString";

const getTranslation = (state, code, args) => {
	if (isString(state.locale) && state.i18n !== undefined){
		const template = state.i18n.getTemplate(code, state.locale);
		return new TemplateEngine().execute(template, args);
	}
	return code;
};

export {
	getTranslation
};
