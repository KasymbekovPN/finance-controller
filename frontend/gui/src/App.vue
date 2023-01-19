<template>
  <v-main-container></v-main-container>
</template>

<script>
	import { CONNECTION_CREATE } from "./store/sconst/connection";
	import vMainContainer from './components/v-main-container';

	export default {
		name: 'App',
		components: {
			vMainContainer
		},
		mounted(){
			this.$nextTick(() => {
				this.$store.dispatch(CONNECTION_CREATE);
			});
		}
	}
</script>

<style lang="scss">
	html, body, div {
		height: 100%;
		margin: 0;
	}

	.app {
		font-family: $commonFontFamily;
		-webkit-font-smoothing: antialiased;
		-moz-osx-font-smoothing: grayscale;
		text-align: center;
	}
</style>
