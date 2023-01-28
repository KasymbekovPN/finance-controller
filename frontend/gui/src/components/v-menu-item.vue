<template>
	<div class="v-menu-item">
		<div class="v-menu-item__content">
			<img
				class="v-menu-item__content__icon"
				:src="require(`../assets/icons/${data.icon}`)"
				alt=""
			>
			<button
				class="v-menu-item__content__button"
				@click="onClick"
			>
				{{ translate(data.code) }}
			</button>
		</div>
	</div>
</template>

<script>
	import { mapGetters } from 'vuex';
	//<
	// import router from '../router/router';

	export default {
		name: 'v-menu-item',
		components: {},
		props: {
			data: Object
		},
		data() {
			return {}
		},
		computed: {
			...mapGetters([
				'translate'
			])
		},
		methods: {
			onClick: function(){
				console.log(`path: ${this.data.path}`);
				//<
				// router.push(this.data.destination);
			}
		}
	}
</script>

<style lang="scss">
	.v-menu-item {
		display: flex;
		width: 150px;
		height: 40px;
		border: 0px solid black;
		margin-top: 10px;
		margin-left: 10px;

		&__content {
			display: flex;
			width: 148px;
			border: 0px solid black;
			background: $buttonBackground;
			margin-left: 10px;

			&__icon {
				width: 20px;
				margin-left: 10px;
				margin-right: 10px;
				height: 30px;
				margin-top: 5px;
			}

			&__button {
				background: $buttonBackground;
				border: 0;
				font-size: 18px;
				font-family: $commonFontFamily;
				height: 30px;
				margin-top: 5px;
				cursor: $buttonCursor
			}
		}
	}
</style>
