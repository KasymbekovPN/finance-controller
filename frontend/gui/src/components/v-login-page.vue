<template>
	<div class="v-login-page">
		<form @submit.prevent="login">
			<div class="v-login-page__form">
				<div>
					<p class="v-login-page__form__title">{{ translate('login-page.title') }}</p>
				</div>
				<div>
					<input
						class="v-login-page__form__username"
						required
						v-model="username"
						type="text"
						:placeholder="[[ translate('login-page.input.username.placeholder') ]]"
					/>
				</div>
				<div>
					<input
						class="v-login-page__form__password"
						required
						v-model="password"
						type="password"
						:placeholder="[[ translate('login-page.input.password.placeholder') ]]"
					/>
				</div>
				<div>
					<hr />
				</div>
				<div class="v-login-page__form__btn-div">
					<button
						class="v-login-page__form__btn-div__button"
						type="submit"
					>
						{{ translate('login-page.button.login') }}
					</button>
				</div>
				<div>
					<p>{{ loginStatus }}</p>
				</div>
			</div>
		</form>
	</div>
</template>

<script>
	import { mapGetters } from 'vuex';
	import { AUTH } from '../sconst/auth';

	export default {
		name: 'v-login-page',
		data() {
			return {
				username: '',
				password: ''
			}
		},
		computed: {
			...mapGetters([
				'translate',
				'authStatus'
			]),
			loginStatus: function(){
				return this.authStatus === AUTH.STATUS.ERROR ? this.translate('login-page.state.error') : '';
			}
		},
		methods: {
			login: function() {
				const user = {username: this.username, password: this.password};
				this.$store.dispatch(AUTH.LOGIN.REQUEST, user);
			}
		}
	}
</script>

<style lang="scss">
	.v-login-page {
		align-items: center;
		font-family: $commonFontFamily;

		&__form {
			display: inline-block;
			width: 400px;
			padding-top: 100px;

			&__title {
				color: $connectionColor;
				font-size: 30px;
			}

			&__username {
				width: 400px;
				height: 24px;
				margin-bottom: 10px;
				font-size: 20px;
			}

			&__password {
				width: 400px;
				height: 24px;
				font-size: 20px;
			}

			&__btn-div {
				display: flex;

				&__button {
					width: 150px;
					height: $buttonHeight;
					font-size: $buttonFontSize;
					background: $buttonBackground;
					border-width: $buttonBorderWisth;
				}
			}
		}
	}
</style>
