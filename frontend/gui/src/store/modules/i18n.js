import I18n from "../../i18n/i18n";
import { createTrTemplates } from "../../i18n/trTemplates";
import { I18N_INIT } from "../actions/i18n";

const state = {
	i18n: undefined
};

const getters = {};

const actions = {
	[I18N_INIT]: ({commit}, {data}) => {
		commit(I18N_INIT, data);
	}
};

const mutations = {
	[I18N_INIT]: (state, data) => {
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
