export default {
	webSocket: {
		url: 'ws://localhost:8081/greetingRequest',
		client: {
			reconnectDelay: 5_000
		}
	},
	menu: {
		items: [
			{
				icon: 'action.svg',
				code: 'menu.item.name.actions',
				path: 'actions'
			},
			{
				icon: 'payment.svg',
				code: 'menu.item.name.payments',
				path: 'payments'
			},
			{
				icon: 'seller.svg',
				code: 'menu.item.name.sellers',
				path: 'sellers'
			},
			{
				icon: 'address.svg',
				code: 'menu.item.name.addresses',
				path: 'addreses'
			},
			{
				icon: 'street.svg',
				code: 'menu.item.name.streets',
				path: 'streets'
			},
			{
				icon: 'city.svg',
				code: 'menu.item.name.cities',
				path: 'cities'
			},
			{
				icon: 'region.svg',
				code: 'menu.item.name.regions',
				path: 'regions'
			},
			{
				icon: 'coutry.svg',
				code: 'menu.item.name.countries',
				path: 'countries'
			},
			{
				icon: 'product.svg',
				code: 'menu.item.name.products',
				path: 'products'
			},
			{
				icon: 'tag.svg',
				code: 'menu.item.name.tags',
				path: 'tags'
			}
		]
	}
};
