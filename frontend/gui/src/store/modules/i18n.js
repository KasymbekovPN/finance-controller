import { I18N_SET_LOCALE, I18N_SET_TEMPLATES } from "../sconst/i18n";
import { getTranslation } from "../imps/i18n-getters";
import { setI18nLocale, setI18nTemplates } from "../imps/i18n-actions";
import { mutateOnSetLocale, mutateOnSetTemplates } from "../imps/i18n-mutations";

const state = {
	i18n: undefined,
	locale: 'en'
};

const getters = {
	translate: state => (code, args) => { getTranslation(state, code, args); }
};

const actions = {
	[I18N_SET_LOCALE]: setI18nLocale,
	[I18N_SET_TEMPLATES]: setI18nTemplates
};

const mutations = {
	[I18N_SET_LOCALE]: mutateOnSetLocale,
	[I18N_SET_TEMPLATES]: mutateOnSetTemplates
};

export default {
	state,
	getters,
	actions,
	mutations
};
