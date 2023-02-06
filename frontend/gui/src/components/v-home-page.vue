<template>
	<div class="v-home-page">
		<p>HOME</p>
		<v-notification-item
			:seed="seed"
			:level="level"
		/>
	</div>
</template>

<script>
	//< tmp
	import vNotificationItem from './notification/v-notification-item';
	import { NOTIFICATION } from '../sconst/notification';

	export default {
		name: 'v-home-page',
		components: {
			//< tmp
			vNotificationItem
		},
		props: {},
		data() {
			return {
				//< tmp
				seed: {},
				level: NOTIFICATION.INFO
			}
		},
		computed: {},
		methods: {}
	}
</script>

<style lang="scss">
	.v-home-page {
		display: flex;
		flex-direction: column;
		align-items: center;
		font-family: $commonFontFamily;
	}
</style>
