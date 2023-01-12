import { TemplateEngine } from "../../utils/templateEngine";
import I18n from "../../i18n/i18n";
import { createTrTemplates,  } from "../../i18n/trTemplates";
import { I18N_SET_LOCALE, I18N_SET_TEMPLATES } from "../sconst/i18n";

const state = {
	i18n: undefined,
	locale: 'en'
};

const getters = {
	translate: state => (code, args) => {
		if (state.locale != undefined && state.i18n !== undefined){
			const template = state.i18n.getTemplate(code, state.locale);
			return new TemplateEngine().execute(template, args);
		}
		return code;
	}
};

const actions = {
	[I18N_SET_LOCALE]: ({commit}, locale) => {
		commit(I18N_SET_LOCALE, locale);
	},
	[I18N_SET_TEMPLATES]: ({commit}, data) => {
		commit(I18N_SET_TEMPLATES, data);
	}
};

const mutations = {
	[I18N_SET_LOCALE]: (state, locale) => {
		state.locale = locale;
	},
	[I18N_SET_TEMPLATES]: (state, data) => {
		const i18n = new I18n();
		for(const code in data.templates){
			const result = createTrTemplates(code, data.templates[code]);
			if (result.success){
				i18n.addTemplate(result.value);
			}
		}
		state.i18n = i18n;
	}
};

export default {
	state,
	getters,
	actions,
	mutations
};
