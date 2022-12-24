<template>
  <p>Temp. text</p>
</template>

<script>
	export default {
		name: 'App',
		components: {},
		//<
		// beforeCreate() {
		// 	//<
		// 	console.log('beforeCreate');
		// },
		// created() {
		// 	//<
		// 	console.log('created');
		// },
		// beforeMount(){
		// 	//<
		// 	console.log('beforeMount');
		// },
		//<
		mounted(){
			//<
			// console.log('mount');
			this.$nextTick(() => {
				//<
				// console.log('mount in');
			});
		},
		//<
		// beforeUpdate() {
		// 	//<
		// 	console.log('beforeUpdate');
		// },
		// updated() {
		// 	//<
		// 	console.log('updated');
		// 	// this.$nextTick(function () {
		// },
		// activated() {
		// 	//<
		// 	console.log('activated');
		// },
		// deactivated() {
		// 	//<
		// 	console.log('deactivated');
		// },
		// beforeUnmount() {
		// 	//<
		// 	console.log('beforeUnmount');
		// },
		// unmounted() {
		// 	//<
		// 	console.log('unmounted');
		// },
		// errorCaptured(err, instance, info) {
		// 	//<
		// 	console.log('errorCaptured', err, instance, info);
		// },
		// renderTracked({ key, target, type }) {
		// 	//<
		// 	console.log('renderTracked', key, target, type);
		// }
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
