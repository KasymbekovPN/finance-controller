<template>
	<div class="v-header">
		<div class="v-header__main">
			<!-- //< -->
			<p>{{ routeName }}</p>
		</div>
		<div class="v-header__logout">
			<button
				class="v-header__logout__button"
				@click="logout"
			>
				{{ translate('btn.logout') }}
			</button>
		</div>
	</div>
</template>

<script>
	import { mapGetters } from 'vuex';
	import { AUTH } from '../sconst/auth';

	export default {
		name: 'v-header',
		data() {
			return {}
		},
		computed: {
			...mapGetters([
				'translate'
			]),
			routeName: function(){
				//<
				console.log(typeof this);
				console.log(this);
				console.log(typeof this.$route);
				console.log(this.$route);
				console.log(typeof this.$route.path);
				console.log(this.$route.path);
				//<
				return this.$route.path;
			}
		},
		methods: {
			logout: function(){
				this.$store.dispatch(AUTH.LOGOUT.REQUEST);
			}
		}
	}
</script>

<style lang="scss">
	.v-header {
        display: flex;
		justify-content: space-between;
		height: 40px;
		background: $headerBackground;

		&__logout {
			padding: 5px;

			&__button {
				width: 100px;
				height: $buttonHeight;
				font-size: $buttonFontSize;
				background: $buttonBackground;
				border-width: $buttonBorderWisth;
				cursor: $buttonCursor;
			}
		}
	}
</style>
