<template>
	<div class="v-main-container">
		<div v-if="isConnected" class="v-main-container__connected">
			<p>CONNECTED</p>
		</div>
		<div v-if="!isConnected" class="v-main-container__disconnected">
			<v-disconnection-element></v-disconnection-element>
		</div>
	</div>
</template>

<script>
	import { mapGetters } from 'vuex';
	import vDisconnectionElement from './v-disconnection-element';

	export default {
		name: 'v-main-container',
		components: {
			vDisconnectionElement
		},
		props: {},
		data() {
			return {
				//< del
				// title: "Main container"
			}
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
		&__disconnected {
			background: $disconnectionBackground;
		}
	}
</style>
