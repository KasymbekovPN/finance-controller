<template>
	<div class="v-tags">
		<p>TAGS</p>
		<v-tags-dialog v-show="isTagAddModalVisible" @close-modal="closeModal" />
	</div>
</template>

<script>
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
				'isTagAddModalVisible'
			])
		},
		methods: {
			closeModal: function(){
				this.$store.dispatch(SIGNAL.MODAL.TAG.ADD.HIDE);
			}
		}
	}
</script>

<style lang="scss">
	.v-tags {
	}
</style>
