const paths = {
	home: '/',
	login: '/login',
	actions: '/actions',
	payments: '/payments',
	sellers: '/sellers',
	addresses: '/addresses',
	streets: '/streets',
	cities: '/cities',
	regions: '/regions',
	countries: '/countries',
	products: '/products',
	tags: '/tags'
};

export default {
	webSocket: {
		url: 'ws://localhost:8081/greetingRequest',
		client: {
			reconnectDelay: 5_000
		}
	},
	requests: {
		auth: '/authRequest',
		i18n: '/i18nRequest',
		clientParams: '/clientParamsRequest',
		logout: '/logoutRequest'
	},
	paths: paths,
	menu: {
		items: [
			{
				icon: 'action.svg',
				code: 'menu.item.name.actions',
				path: paths.actions
			},
			{
				icon: 'payment.svg',
				code: 'menu.item.name.payments',
				path: paths.payments
			},
			{
				icon: 'seller.svg',
				code: 'menu.item.name.sellers',
				path: paths.sellers
			},
			{
				icon: 'address.svg',
				code: 'menu.item.name.addresses',
				path: paths.addresses
			},
			{
				icon: 'street.svg',
				code: 'menu.item.name.streets',
				path: paths.streets
			},
			{
				icon: 'city.svg',
				code: 'menu.item.name.cities',
				path: paths.cities
			},
			{
				icon: 'region.svg',
				code: 'menu.item.name.regions',
				path: paths.regions
			},
			{
				icon: 'country.svg',
				code: 'menu.item.name.countries',
				path: paths.countries
			},
			{
				icon: 'product.svg',
				code: 'menu.item.name.products',
				path: paths.products
			},
			{
				icon: 'tag.svg',
				code: 'menu.item.name.tags',
				path: paths.tags
			}
		]
	}
};
