import { DOMAIN } from "../../sconst/domain";
import {
	actOnTagCreation,
	actOnTagDeleting,
	actOnTagUpdating
} from "../imps/domain-actions";

const state = {};

const getters = {};

const actions = {
	[DOMAIN.TAG.CREATE]: actOnTagCreation,
	[DOMAIN.TAG.UPDATE]: actOnTagUpdating,
	[DOMAIN.TAG.DELETE]: actOnTagDeleting
};

const mutations = {};

export default {
    state,
    getters,
    actions,
    mutations
};
