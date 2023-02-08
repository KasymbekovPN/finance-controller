package kpn.ctrlf.client.conversation.converter;

import kpn.ctrlf.client.conversation.response.args.ErrorArgs;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorArgsConverterHolderImplTest {
	private static final String CODE = "some.code";

	@Test
	void shouldCheckTaking_ifSupplierIsAbsent() {
		ErrorArgsConverterHolderImpl holder = new ErrorArgsConverterHolderImpl();

		ErrorArgsConverter converter = holder.take(CODE);
		assertThat(converter.getClass()).isEqualTo(DefaultErrorArgsConverter.class);
	}

	@Test
	void shouldCheckTaking_ifSupplierIsAdded() {
		ErrorArgsConverterHolder holder = new ErrorArgsConverterHolderImpl()
			.set(CODE, new Supplier<ErrorArgsConverter>() {
				@Override
				public ErrorArgsConverter get() {
					return new TestErrorArgsConverter();
				}
			});

		ErrorArgsConverter converter = holder.take(CODE);
		assertThat(converter.getClass()).isEqualTo(TestErrorArgsConverter.class);
	}

	@Test
	void shouldCheckTaking_ifSupplierIsErased() {
		ErrorArgsConverterHolder holder = new ErrorArgsConverterHolderImpl()
			.set(CODE, new Supplier<ErrorArgsConverter>() {
				@Override
				public ErrorArgsConverter get() {
					return new TestErrorArgsConverter();
				}
			})
			.erase(CODE);

		ErrorArgsConverter converter = holder.take(CODE);
		assertThat(converter.getClass()).isEqualTo(DefaultErrorArgsConverter.class);
	}

	private static class TestErrorArgsConverter implements ErrorArgsConverter {
		@Override
		public ErrorArgs convert(Object... args) { return null; }
	}
}
