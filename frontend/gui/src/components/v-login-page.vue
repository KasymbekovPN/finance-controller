<template>
	<div class="v-login-page">
		<form
			class="v-login-page__form"
			@submit.prevent="login"
		>
			<h1 class="v-login-page__title">{{ title }}</h1>
			<input
				required
				v-model="username"
				type="text"
				:placeholder="[[ usernamePlaceholder ]]"
			/>
			<input
				required
				v-model="password"
				type="password"
				:placeholder="[[ passwordPlaceholder ]]"
			/>
			<hr />
			<button type="submit">{{ buttonText }}</button>
		</form>
	</div>
</template>

<script>
	import { mapState } from 'vuex';
	import { TemplateEngine } from '../utils/templateEngine';
	import { AUTH_LOGIN_REQUEST } from '../store/actions/auth';

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
		display: flex;
		flex-direction: column;
		align-items: center;
		font-family: $commonFontFamily;
	}
</style>
