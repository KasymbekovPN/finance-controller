<template>
	<div class="v-notification-item">
		<div class="v-notification-item-top">
			<button
				class="v-notification-item-close-btn"
				@click="onClose"
			>
				<img
					class="v-notification-item-close-img"
					:src="require(`../../assets/icons/close-cross.svg`)"
					alt=""
				>
			</button>
		</div>
		<div class="v-notification-item-bottom">
			<img
				class="v-notification-item-level-img"
				:src="require(`../../assets/icons/${icon}.svg`)"
				alt=""
			>
			<p class="v-notification-item-content">{{ message }}</p>
		</div>
	</div>
</template>

<script>
	import { mapGetters } from 'vuex';
	import { getIcon } from '../../sconst/notification';

	export default {
		name: 'v-notification-item',
		components: {},
		props: {
			id: String,
			seed: Object,
			level: String
		},
		data() {
			return {}
		},
		computed: {
			...mapGetters([
				'translate'
			]),
			message: function(){
				return this.translate(this.seed.code, this.seed.args);
			},
			icon: function() {
				return getIcon(this.level);
			}
		},
		methods: {
			onClose: function() {
				//<
				console.log(`v-notivication-item onClic id=${this.id}`);
			}
		}
	}
</script>

<style lang="scss">
	.v-notification-item {
		height: 100px;
		width: 400px;
		border: solid $notificationItemBorderColor 1px;
		border-radius: 20px;
		box-shadow: 10px 5px 5px $notificationItemShadowColor;
	}

	.v-notification-item-top {
		display: flex;
		justify-content: flex-end;
		height: 30px;
		margin-right: 5px;
	}

	.v-notification-item-close-btn {
		width: 20px;
		height: 20px;
		background: #ffffff;
		border-width: 0px;
		cursor: $buttonCursor;
		margin-top: 5px;
		margin-left: 5px;
		padding: 0px;
	}

	.v-notification-item-close-img {
		width: 20px;
		height: 20px;
		margin: 0px;
	}

	.v-notification-item-bottom {
		display: flex;
	}

	.v-notification-item-level-img {
		height: 40px;
		width: 40px;
		margin-left: 10px;
	}

	.v-notification-item-content {
		margin-left: 10px;
		margin-top: 0px;
		text-align: left;
	}
</style>
