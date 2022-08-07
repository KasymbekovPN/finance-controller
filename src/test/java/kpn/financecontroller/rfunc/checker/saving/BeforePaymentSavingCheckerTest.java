package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domain.auxi.Currency;
import kpn.financecontroller.data.domain.auxi.Measure;
import kpn.financecontroller.data.domain.Payment;
import kpn.financecontroller.data.domain.Product;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class BeforePaymentSavingCheckerTest {

    @Test
    void shouldCheck_whenProductNull() {
        Payment domain = new Builder().build();
        ImmutableResult<List<Payment>> expectedResult
                = ImmutableResult.<List<Payment>>fail("checking.domain.payment.product.isEmpty");

        Result<List<Payment>> result = new BeforePaymentSavingChecker().apply(domain);
        Assertions.assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenCurrencyNull() {
        Payment domain = new Builder().product(new Product()).build();
        ImmutableResult<List<Payment>> expectedResult
                = ImmutableResult.<List<Payment>>fail("checking.domain.payment.currency.isEmpty");

        Result<List<Payment>> result = new BeforePaymentSavingChecker().apply(domain);
        Assertions.assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenPriceNull() {
        Payment domain = new Builder()
                .product(new Product())
                .currency(Currency.RUB)
                .build();
        ImmutableResult<List<Payment>> expectedResult
                = ImmutableResult.<List<Payment>>fail("checking.domain.payment.price.isEmpty");

        Result<List<Payment>> result = new BeforePaymentSavingChecker().apply(domain);
        Assertions.assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenPriceZero() {
        Payment domain = new Builder()
                .product(new Product())
                .currency(Currency.RUB)
                .price(0.0f)
                .build();
        ImmutableResult<List<Payment>> expectedResult
                = ImmutableResult.<List<Payment>>fail("checking.domain.payment.price.isEmpty");

        Result<List<Payment>> result = new BeforePaymentSavingChecker().apply(domain);
        Assertions.assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenCreatedAtNull() {
        Payment domain = new Builder()
                .product(new Product())
                .currency(Currency.RUB)
                .price(1.0f)
                .build();
        ImmutableResult<List<Payment>> expectedResult
                = ImmutableResult.<List<Payment>>fail("checking.domain.payment.createdAt.isEmpty");

        Result<List<Payment>> result = new BeforePaymentSavingChecker().apply(domain);
        Assertions.assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenOnlyMeasureNotNull_butAmountNull() {
        Payment domain = new Builder()
                .product(new Product())
                .currency(Currency.RUB)
                .price(1.0f)
                .createAt()
                .measure(Measure.KG)
                .build();
        ImmutableResult<List<Payment>> expectedResult
                = ImmutableResult.<List<Payment>>fail("checking.domain.payment.amountAndMeasure.notConsistent");

        Result<List<Payment>> result = new BeforePaymentSavingChecker().apply(domain);
        Assertions.assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenOnlyAmountNotNull_butMeasureNull() {
        Payment domain = new Builder()
                .product(new Product())
                .currency(Currency.RUB)
                .price(1.0f)
                .createAt()
                .amount(1.0f)
                .build();
        ImmutableResult<List<Payment>> expectedResult
                = ImmutableResult.<List<Payment>>fail("checking.domain.payment.amountAndMeasure.notConsistent");

        Result<List<Payment>> result = new BeforePaymentSavingChecker().apply(domain);
        Assertions.assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenAmountZero_ifItNotNull() {
        Payment domain = new Builder()
                .product(new Product())
                .currency(Currency.RUB)
                .price(1.0f)
                .measure(Measure.KG)
                .amount(0.0f)
                .createAt()
                .build();
        ImmutableResult<List<Payment>> expectedResult
                = ImmutableResult.<List<Payment>>fail("checking.domain.payment.amount.isEmpty");

        Result<List<Payment>> result = new BeforePaymentSavingChecker().apply(domain);
        Assertions.assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck() {
        Payment domain = new Builder()
                .product(new Product())
                .currency(Currency.RUB)
                .price(1.0f)
                .createAt()
                .build();
        ImmutableResult<List<Payment>> expectedResult = ImmutableResult.<List<Payment>>ok(List.of(domain));

        Result<List<Payment>> result = new BeforePaymentSavingChecker().apply(domain);
        Assertions.assertThat(expectedResult).isEqualTo(result);
    }

    private static class Builder {
        private Product product;
        private Currency currency;
        private Float price;
        private Float amount;
        private LocalDate createAt;
        private Measure measure;

        public Payment build() {
            Payment payment = new Payment();
            payment.setProduct(product);
            payment.setCurrency(currency);
            payment.setPrice(price);
            payment.setAmount(amount);
            payment.setCreatedAt(createAt);
            payment.setMeasure(measure);
            return payment;
        }

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public Builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public Builder price(float price) {
            this.price = price;
            return this;
        }

        public Builder amount(float amount) {
            this.amount = amount;
            return this;
        }

        public Builder createAt() {
            this.createAt = LocalDate.now();
            return this;
        }

        public Builder measure(Measure measure) {
            this.measure = measure;
            return this;
        }
    }
}