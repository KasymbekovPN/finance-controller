<template>
  <p class="p0">Temp. text</p>
</template>

<script>
	import { Stomp } from "@stomp/stompjs";
	import { CONNECTION_CREATE } from "./store/actions/connection";
	import { v4 as uuidv4 } from 'uuid';
	import config from '../config.js';

	export default {
		name: 'App',
		components: {},
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
	.p0 {
		color: $tmp_color
	}

	#app {
		font-family: Avenir, Helvetica, Arial, sans-serif;
		-webkit-font-smoothing: antialiased;
		-moz-osx-font-smoothing: grayscale;
		text-align: center;
		color: #2c3e50;
		margin-top: 60px;
	}
</style>
