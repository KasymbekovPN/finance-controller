import { I18N_SET_LOCALE, I18N_SET_TEMPLATES } from "../sconst/i18n";

const setI18nLocale = ({commit}, locale) => {
	commit(I18N_SET_LOCALE, locale);
};

const setI18nTemplates = ({commit}, data) => {
	commit(I18N_SET_TEMPLATES, data);
};

export {
	setI18nLocale,
	setI18nTemplates
};
