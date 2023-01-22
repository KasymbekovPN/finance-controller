<template>
	<div class="v-home-page">
		<p>HOME</p>
		<button
			class="tmp-btn"
			@click="logout"
		>
			LOGOUT
		</button>
	</div>
</template>

<script>
	import { AUTH } from '../sconst/auth';

	export default {
		name: 'v-home-page',
		components: {},
		props: {},
		data() {
			return {}
		},
		computed: {},
		methods: {
			logout: function(){
				this.$store.dispatch(AUTH.LOGOUT.REQUEST);
			}
		}
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
