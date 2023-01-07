<template>
	<div class="v-disconnection-element">
		<svg
			version="1.1"
			xmlns="http://www.w3.org/2000/svg"
			xmlns:xlink="http://www.w3.org/1999/xlink"
			x="0px"
			y="0px"
			width="200px"
			height="200px"
			viewBox="0 0 50 50"
			style="enable-background:new 0 0 50 50;"
			xml:space="preserve"
		>
			<path
				fill="#000"
				d="M25.251,6.461c-10.318,0-18.683,8.365-18.683,18.683h4.068c0-8.071,6.543-14.615,14.615-14.615V6.461z"
			>
				<animateTransform
					attributeType="xml"
					attributeName="transform"
					type="rotate"
					from="0 25 25"
					to="360 25 25"
					dur="1s"
					repeatCount="indefinite"
				/>
			</path>
		</svg>
		<p class="v-disconnection-element__label">NO CONNECTION</p>
	</div>
</template>

<script>
	export default {
		name: 'v-disconnection-element',
		data() {
			return {}
		}
	}
</script>

<style lang="scss">
	.v-disconnection-element {
		text-align: center;
		display: block;

		&__label {
			font-family: "Roboto", sans-serif;
			color: $disconnectionColor;
		}
	}

	svg path {
		fill: $disconnectionColor;
	}
</style>
