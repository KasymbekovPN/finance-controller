<template>
	<div class="v-notification-item">
		<div class="v-notification-item-top">
			<button class="v-notification-item-close-btn">
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
				:src="require(`../../assets/icons/info.svg`)"
				alt=""
			>
			<p class="v-notification-item-content"> Message </p>
		</div>
	</div>
</template>

<script>
	export default {
		name: 'v-notification-item',
		components: {},
		props: {
			seed: Object,
			level: String
		},
		data() {
			return {}
		},
		computed: {},
		methods: {}
	}
</script>

<style lang="scss">
	.v-notification-item {
		border: solid red 1px;
		height: 700px;
		width: 700px;
	}

	.v-notification-item-top {
		display: flex;
		justify-content: flex-end;
		border: solid red 1px;
		height: 30px;
		margin-right: 5px;
	}

	.v-notification-item-close-btn {
		width: 20px;
		height: 20px;
		background: #ffffff;;
		border-width: 1px;
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

	.v-notification-item-bottom {}

	.v-notification-item-level-img {}

	.v-notification-item-content {}

</style>
