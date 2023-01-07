<template>
  <v-main-container></v-main-container>
</template>

<script>
	import { Stomp } from "@stomp/stompjs";
	import { CONNECTION_CREATE } from "./store/actions/connection";
	import { v4 as uuidv4 } from 'uuid';
	import config from '../config.js';

	import vMainContainer from './components/v-main-container';

	export default {
		name: 'App',
		components: {
			vMainContainer
		},
		mounted(){
			this.$nextTick(() => {
				const clientCreator = () => {
					const client = Stomp.over(() => {return new WebSocket(config.webSocket.url)});
					client.reconnect_delay = config.webSocket.client.reconnectDelay;
					return client;
				};
				const connectionHeaders = {};
				const sessionId = uuidv4();
				this.$store.dispatch(CONNECTION_CREATE, {clientCreator, connectionHeaders, sessionId});
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
