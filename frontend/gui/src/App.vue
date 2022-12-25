<template>
  <p>Temp. text</p>
</template>

<script>
	import { Stomp } from "@stomp/stompjs";
	import { CONNECTION_CREATE } from "./store/actions/connection";
	import { v4 as uuidv4 } from 'uuid';

	export default {
		name: 'App',
		components: {},
		mounted(){
			this.$nextTick(() => {
				const clientCreator = () => {
					const client = Stomp.over(() => {return new WebSocket('ws://localhost:8080/greetingRequest')});
					client.reconnect_delay = 5_000;
					return client;
				};
				const connectionHeaders = {};
				const sessionId = uuidv4();
				this.$store.dispatch(CONNECTION_CREATE, {clientCreator, connectionHeaders, sessionId});
			});
		}
	}
</script>

<style>
	#app {
		font-family: Avenir, Helvetica, Arial, sans-serif;
		-webkit-font-smoothing: antialiased;
		-moz-osx-font-smoothing: grayscale;
		text-align: center;
		color: #2c3e50;
		margin-top: 60px;
	}
</style>
