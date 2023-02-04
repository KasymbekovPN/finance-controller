<template>
	<div class="v-tags">
		<p>TAGS</p>
		<v-tags-dialog v-show="isTagModalVisible" @close-modal="closeModal" />
	</div>
</template>

<script>
	import config from '../../config';
	import { mapGetters } from 'vuex';
	import { SIGNAL } from '../sconst/signal';
	import vTagsDialog from './v-tags-dialog';

	export default {
		name: 'v-tags',
		components: {
			vTagsDialog
		},
		props: {},
		data() {
			return {}
		},
		computed: {
			...mapGetters([
				'isTagModalVisible'
			])
		},
		methods: {
			closeModal: function(){
				this.$store.dispatch(SIGNAL.MODAL.SOME.ADD.HIDE, config.paths.tags);
			}
		}
	}
</script>

<style lang="scss">
	.v-tags {
	}
</style>
