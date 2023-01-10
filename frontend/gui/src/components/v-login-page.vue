<template>
	<div class="v-login-page">
		<form @submit.prevent="login">
			<div class="v-login-page__form">
				<div>
					<p class="v-login-page__form__title">{{ title }}</p>
				</div>
				<div>
					<input
						class="v-login-page__form__username"
						required
						v-model="username"
						type="text"
						:placeholder="[[ usernamePlaceholder ]]"
					/>
				</div>
				<div>
					<input
						class="v-login-page__form__password"
						required
						v-model="password"
						type="password"
						:placeholder="[[ passwordPlaceholder ]]"
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
						{{ buttonText }}
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
	import { mapState } from 'vuex';
	import { TemplateEngine } from '../utils/templateEngine';
	import { AUTH_LOGIN_REQUEST } from '../store/actions/auth';
	import { AUTH_STATUS_ERROR } from '../store/status/auth';

	//< tmp FC-000176
	const translate = (state, code, args) => {
		if (args === undefined){
			args= new Map();
		}

		const locale = state.clientParams.locale;
		const i18n = state.i18n.i18n;

		if (locale != undefined && i18n !== undefined){
			const template = i18n.getTemplate(code, locale);
			return new TemplateEngine().execute(template, args);
		}

		return code;
	};

	export default {
		name: 'v-login-page',
		data() {
			return {
				username: '',
				password: ''
			}
		},
		computed: {
			...mapState({
				title: state => {
					return translate(state, 'login-page.title');
				},
				buttonText: state => {
					return translate(state, 'login-page.button.login');
				},
				usernamePlaceholder: state => {
					return translate(state, 'login-page.input.username.placeholder');
				},
				passwordPlaceholder: state => {
					return translate(state, 'login-page.input.password.placeholder');
				},
				loginStatus: state => {
					return state.auth.status === AUTH_STATUS_ERROR ? translate(state, 'login-page.state.error') : '';
				}
			})
		},
		methods: {
			login: function() {
				const user = {username: this.username, password: this.password};
				this.$store.dispatch(AUTH_LOGIN_REQUEST, user);
			}
		}
	}
</script>

<style lang="scss">
	.v-login-page {
		align-items: center;
		font-family: $commonFontFamily;
		padding-top: 100px;

		&__form {
			display: inline-block;
			width: 400px;
			padding-top: 0px;

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
					height: 30px;
					font-size: 20px;
					background: #bacde4;
					border-width: 0px;
				}
			}
		}
	}
</style>
