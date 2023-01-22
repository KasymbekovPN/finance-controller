import { I18N } from "../../sconst/i18n";

const setI18nLocale = ({commit}, locale) => {
	commit(I18N.SET.LOCALE, locale);
};

const setI18nTemplates = ({commit}, data) => {
	commit(I18N.SET.TEMPLATES, data);
};

export {
	setI18nLocale,
	setI18nTemplates
};
