<template>
	<div class="v-main-container">
		<div v-if="isConnected" class="v-main-container__connected">
			<v-header v-if="isAuthenticated"/>
			<div class="v-main-container__connected__main">
				<!-- //< one prop data -->
				<v-menu-item
					v-if="isAuthenticated"
					:icon="item.icon"
					:code="item.code"
					:destination="item.destination"
				/>
				<div class="v-main-container__connected__main__router_view">
					<router-view />
				</div>
			</div>
			<!-- //< -->
			<!-- <router-view /> -->
		</div>
		<div v-if="!isConnected" class="v-main-container__disconnected">
			<v-disconnection-page></v-disconnection-page>
		</div>
	</div>
</template>

<script>
	import { mapGetters } from 'vuex';
	import vDisconnectionPage from './v-disconnection-page';
	import vHeader from './v-header';
	//<
	import vMenuItem from './v-menu-item';

export default {
		name: 'v-main-container',
		components: {
			vDisconnectionPage,
			vHeader,
			vMenuItem
		},
		props: {},
		data() {
			return {
				item : {
					icon: 'tag.svg',
					code: 'tags',
					destination: 'tags'
				}
			}
		},
		computed: {
			...mapGetters([
				'isConnected',
				'isAuthenticated'
			])
		},
		methods: {}
	}
</script>

<style lang="scss">
	.v-main-container {
		text-align: center;

		&__connected {
			background: $connectionBackground;

			&__main {
				display: flex;

				&__router_view {
					width: 100%;
				}
			}
		}

		&__disconnected {
			background: $disconnectionBackground;
		}
	}
</style>
