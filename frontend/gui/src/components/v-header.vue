<template>
	<div class="v-header">
		<div class="v-header__main">
			<button
				v-if="!isHomeRoute"
				class="v-header__main__btn"
				@click="onHome"
			>
				<img
					class="v-header__main__btn__img"
					:src="require(`../assets/icons/home.svg`)"
					alt=""
				/>
			</button>
			<button
				v-if="!isHomeRoute"
				class="v-header__main__btn"
				@click="onAdd"
			>
				<img
					class="v-header__main__btn__img"
					:src="require(`../assets/icons/add.svg`)"
					alt=""
				/>
			</button>

		</div>
		<div class="v-header__logout">
			<button
				class="v-header__logout__button"
				@click="logout"
			>
				{{ translate('btn.logout') }}
			</button>
		</div>

		<!-- //< -->
		<v-tags-dialog v-show="showModal" @close-modal="showModal = false" />
	</div>
</template>

<script>
	import config from '../../config';
	import { mapGetters } from 'vuex';
	import { AUTH } from '../sconst/auth';
	import router from '../router/router';

	//<
	import vTagsDialog from './v-tags-dialog';

	export default {
		name: 'v-header',
		components: {
			vTagsDialog
		},
		data() {
			return {
				showModal: false
			}
		},
		computed: {
			...mapGetters([
				'translate',
				'isHomeRoute'
			])
		},
		methods: {
			logout: function(){
				this.$store.dispatch(AUTH.LOGOUT.REQUEST);
			},
			onHome: function() {
				router.push(config.paths.home);
			},
			onAdd: function() {
				//<
				console.log('ON ADD');
				//<
				this.showModal = true;
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

		&__main {
			margin-left: 185px;

			&__btn {
				width: 40px;
				height: $buttonHeight;
				font-size: $buttonFontSize;
				background: $buttonBackground;
				border-width: $buttonBorderWisth;
				cursor: $buttonCursor;
				margin-top: 5px;
				margin-left: 5px;

				&__img {
					height: 25px;
					margin-top: 2px;
				}
			}
		}

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
