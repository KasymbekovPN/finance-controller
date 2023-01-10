<template>
	<div class="v-main-container">
		<div v-if="isConnected" class="v-main-container__connected">
			<router-view />
		</div>
		<div v-if="!isConnected" class="v-main-container__disconnected">
			<v-disconnection-page></v-disconnection-page>
		</div>
	</div>
</template>

<script>
	import { mapGetters } from 'vuex';
	import vDisconnectionPage from './v-disconnection-page';

export default {
		name: 'v-main-container',
		components: {
			vDisconnectionPage
		},
		props: {},
		data() {
			return {}
		},
		computed: {
			...mapGetters([
				"isConnected"
			])
		},
		methods: {}
	}
</script>

<style lang="scss">
	.v-main-container {
		text-align: center;

		&__connected {
			background: $connectionBackground;
		}

		&__disconnected {
			background: $disconnectionBackground;
		}
	}
</style>
