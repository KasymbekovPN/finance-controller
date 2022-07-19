package kpn.financecontroller.data.domains.payment;

import kpn.financecontroller.data.domains.seller.Seller;
import kpn.financecontroller.data.domains.product.Product;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentTest {

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckGetting.csv")
    void shouldCheckGetting(Long id,
                            String productAnswer,
                            float price,
                            Currency currency,
                            float amount,
                            Measure measure,
                            String sellerAnswer,
                            String rawLocalDate,
                            String rawPath,
                            String expectedResult) {
        ArrayDeque<String> path = new ArrayDeque<>(List.of(rawPath.split("\\.")));
        Payment domain = new Payment();
        domain.setId(id);
        domain.setProduct(createProduct(productAnswer, path));
        domain.setPrice(price);
        domain.setCurrency(currency);
        domain.setAmount(amount);
        domain.setMeasure(measure);
        domain.setSeller(createSeller(sellerAnswer, path));
        domain.setCreatedAt(LocalDate.parse(rawLocalDate));

        String result = domain.getInDeep(path);
        assertThat(expectedResult).isEqualTo(result);
    }

    private Product createProduct(String productAnswer, ArrayDeque<String> path) {
        Product product = Mockito.mock(Product.class);
        Mockito
                .when(product.getInDeep(path))
                .thenReturn(productAnswer);
        return product;
    }

    private Seller createSeller(String placeAnswer, ArrayDeque<String> path) {
        Seller seller = Mockito.mock(Seller.class);
        Mockito
                .when(seller.getInDeep(path))
                .thenReturn(placeAnswer);
        return seller;
    }
}