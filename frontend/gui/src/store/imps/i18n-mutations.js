import I18n from "../..//i18n/i18n";
import { createTrTemplates } from "../../i18n/trTemplates";

const mutateOnSetLocale = (state, locale) => {
	state.locale = locale;
};

const mutateOnSetTemplates = (state, data) => {
	const i18n = new I18n();
	for(const code in data.templates){
		const result = createTrTemplates(code, data.templates[code]);
		if (result.success){
			i18n.addTemplate(result.value);
		}
	}
	state.i18n = i18n;
};

export {
	mutateOnSetLocale,
	mutateOnSetTemplates
};
