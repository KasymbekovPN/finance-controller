<template>
	<div class="v-main-container">
		<div v-if="isConnected" class="v-main-container__connected">
			<v-header v-if="isAuthenticated"/>
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
	import vHeader from './v-header';

export default {
		name: 'v-main-container',
		components: {
			vDisconnectionPage,
			vHeader
		},
		props: {},
		data() {
			return {}
		},
		computed: {
			...mapGetters([
				'isConnected',
				'isAuthenticated'
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
