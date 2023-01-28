<template>
	<div class="v-menu">
		<v-menu-item
			v-for="item in items"
			:key="item.code"
			:data="item"
		/>
	</div>
</template>

<script>
	import vMenuItem from './v-menu-item';

	export default {
		name: 'v-menu',
		components: {
			vMenuItem
		},
		props: {
			items: Array
		},
		data() {
			return {}
		}
	}
</script>

<style lang="scss">
	.v-menu {
		width: 220px;
		border-right: 1px solid $verticalSplitterColor;
	}
</style>
