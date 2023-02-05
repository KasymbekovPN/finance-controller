<template>
  <transition name="modal-fade">
    <div
		class="modal-overlay"
		@click="$emit('close-modal')"
	>
    <div
		class="modal"
		@click.stop
	>
		<p class="modal-title">{{ translate('dialog.tag.title') }}</p>
		<hr>
		<input
			class="modal-name-input"
			v-model="name"
			:placeholder="[[ translate('dialog.tag.input.placeholder') ]]"
		/>
		<div class="modal-buttons">
			<button
				class="modal-btn-save"
				@click="onSaveButtonClick"
			>
				{{ translate('dialog.btn.save.name') }}
			</button>
			<button
				class="modal-btn-close"
				@click="$emit('close-modal')"
			>
				{{ translate('dialog.btn.close.name') }}
			</button>
			<!-- //< v-if!!! -->
			<button
				class="modal-btn-delete"
				@click="onDeleteButtonClick"
			>{{ translate('dialog.btn.delete.name') }}</button>
		</div>
      </div>
    </div>
  </transition>
</template>

<script>
	import { mapGetters, mapActions } from 'vuex';
	import { DOMAIN } from '../sconst/domain';

	export default {
		name: 'v-header',
		components: {},
		data() {
			return {
				id : undefined,
				name: ''
			}
		},
		computed: {
			...mapGetters([
				'translate'
			])
		},
		methods: {
			...mapActions({
				sendNewTag: DOMAIN.TAG.CREATE
			}),
			onSaveButtonClick: function() {
				this.sendNewTag({name: this.name});
				this.$emit('close-modal');
			},
			onDeleteButtonClick: function() {}
		}
	}
</script>

<style lang="scss">

	.modal-fade-enter,
	.modal-fade-leave-to {
		opacity: 0;
	}
	.modal-fade-enter-active,
	.modal-fade-leave-active {
		transition: opacity 0.5s ease;
	}

	.modal-overlay {
		position: fixed;
		top: 0;
		bottom: 0;
		left: 0;
		right: 0;
		display: flex;
		justify-content: center;
		background-color: #000000da;
	}

	.modal {
		text-align: center;
		background-color: white;
		height: 200px;
		width: 400px;
		margin-top: 10%;
		padding: 15px 0;
		border-radius: 20px;
	}

	.modal-title {
		font-size: 24px;
		font-family: $commonFontFamily;
	}

	.modal-name-input {
		height: 24px;
		width: 300px;
	}

	.modal-btn-save {
		width: 80px;
		height: $buttonHeight;
		font-size: $buttonFontSize;
		background: $buttonBackground;
		border-width: $buttonBorderWisth;
		cursor: $buttonCursor;
		margin-top: 15px;
	}

	.modal-btn-close {
		width: 80px;
		height: $buttonHeight;
		font-size: $buttonFontSize;
		background: $buttonBackground;
		border-width: $buttonBorderWisth;
		cursor: $buttonCursor;
		margin-top: 15px;
		margin-left: 5px;
	}

	.modal-btn-delete {
		width: 80px;
		height: $buttonHeight;
		font-size: $buttonFontSize;
		background: $buttonBackground;
		border-width: $buttonBorderWisth;
		cursor: $buttonCursor;
		margin-top: 15px;
		margin-left: 5px;
	}
</style>
