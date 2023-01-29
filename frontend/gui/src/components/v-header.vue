<template>
	<div class="v-header">
		<div class="v-header__main">
			<!-- //< -->
			<!-- <p>{{ routeName }}</p> -->

			<!-- //< temp -->
			<p v-if="isTagsRoute">isTagsRoute</p>
			<p v-if="isProductsRoute">isProductsRoute</p>
			<p v-if="isCountriesRoute">isCountriesRoute</p>
			<p v-if="isRegionsRoute">isRegionsRoute</p>
			<p v-if="isCitiesRoute">isCitiesRoute</p>
			<p v-if="isStreetsRoute">isStreetsRoute</p>
			<p v-if="isAddressesRoute">isAddressesRoute</p>
			<p v-if="isSellersRoute">isSellersRoute</p>
			<p v-if="isPaymentsRoute">isPaymentsRoute</p>
			<p v-if="isActionsRoute">isActionsRoute</p>
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
				'translate',
				'isTagsRoute',
				'isProductsRoute',
				'isCountriesRoute',
				'isRegionsRoute',
				'isCitiesRoute',
				'isStreetsRoute',
				'isAddressesRoute',
				'isSellersRoute',
				'isPaymentsRoute',
				'isActionsRoute'
			])
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
