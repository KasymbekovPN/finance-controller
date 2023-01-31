<template>
  <transition name="modal-fade">
    <div class="modal-overlay" @click="$emit('close-modal')">
      <div class="modal" @click.stop>
		<!-- //< translate -->
		<p class="modal-title">Tag editor</p>
		<hr>
		<!-- //< translate -->
		<input class="modal-name-input" v-model="name" placeholder="type name..." />
		<div class="modal-buttons">
			<!-- //< translate -->
			<button class="modal-btn-save">Save</button>
			<!-- //< translate -->
			<button class="modal-btn-close">Close</button>
			<!-- //< translate v-if!!! -->
			<button class="modal-btn-delete">Delete</button>
		</div>
      </div>
    </div>
  </transition>
</template>

<script>
	export default {
		name: 'v-header',
		components: {},
		data() {
			return {
				name: ''
			}
		},
		computed: {},
		methods: {}
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

<!-- //< del -->

<!-- <style lang="scss">
.modal-overlay {

}
.modal {

}
.close {
  margin: 10% 0 0 16px;
  cursor: pointer;
}
.close-img {
  width: 25px;
}
.check {
  width: 150px;
}
h6 {
  font-weight: 500;
  font-size: 28px;
  margin: 20px 0;
}
p {
  /* font-weight: 500; */
  font-size: 16px;
  margin: 20px 0;
}
button {
  background-color: #ac003e;
  width: 150px;
  height: 40px;
  color: white;
  font-size: 14px;
  border-radius: 16px;
  margin-top: 50px;
}

</style> -->



<!-- //< -->
<!-- <template>
  <transition name="modal-fade">
    <div class="modal-overlay" @click="$emit('close-modal')">
      <div class="modal" @click.stop>
        <img class="check" src="../assets/icons/add.svg" alt="" />
        <h6>Saved!</h6>
        <p>Your Details have been saved Successfully</p>
        <button>Go Home</button>
      </div>
      <div class="close" @click="$emit('close-modal')">
        <img class="close-img" src="../assets/icons/add.svg" alt="" />
      </div>
    </div>
  </transition>
</template>

<script>
export default {}
</script>

<style scoped>
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
  height: 500px;
  width: 500px;
  margin-top: 10%;
  padding: 60px 0;
  border-radius: 20px;
}
.close {
  margin: 10% 0 0 16px;
  cursor: pointer;
}
.close-img {
  width: 25px;
}
.check {
  width: 150px;
}
h6 {
  font-weight: 500;
  font-size: 28px;
  margin: 20px 0;
}
p {
  /* font-weight: 500; */
  font-size: 16px;
  margin: 20px 0;
}
button {
  background-color: #ac003e;
  width: 150px;
  height: 40px;
  color: white;
  font-size: 14px;
  border-radius: 16px;
  margin-top: 50px;
}
.modal-fade-enter,
.modal-fade-leave-to {
  opacity: 0;
}
.modal-fade-enter-active,
.modal-fade-leave-active {
  transition: opacity 0.5s ease;
}
</style> -->
