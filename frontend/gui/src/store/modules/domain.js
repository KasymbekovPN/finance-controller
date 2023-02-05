import { DOMAIN } from "../../sconst/domain";
import {
	processTagCreationRequest,
	processTagDeletingRequest,
	processTagUpdatingRequest
} from "../imps/domain-actions";

const state = {};

const getters = {};

const actions = {
	[DOMAIN.TAG.REQUEST.CREATE]: processTagCreationRequest,
	[DOMAIN.TAG.REQUEST.UPDATE]: processTagUpdatingRequest,
	[DOMAIN.TAG.REQUEST.DELETE]: processTagDeletingRequest
};

const mutations = {};

export default {
    state,
    getters,
    actions,
    mutations
};
