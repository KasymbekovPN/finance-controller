<template>
	<div class="v-home-page">
		<p>HOME</p>
		<!-- //< temp -->
		<v-notification-item
			:id="id"
			:seed="seed"
			:level="level"
		/>
	</div>
</template>

<script>
	//< tmp
	import vNotificationItem from './notification/v-notification-item';
	import { NOTIFICATION } from '../sconst/notification';
	//<

	export default {
		name: 'v-home-page',
		components: {
			//< tmp
			vNotificationItem
			//<
		},
		props: {},
		data() {
			return {
				//< tmp
				id: 'some.id',
				seed: {
					code: '{arg0} some.code {arg1}',
					args: {
						arg0: 'value0',
						arg1: 'value1'
					}
				},
				level: NOTIFICATION.WARNING
				//<
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
