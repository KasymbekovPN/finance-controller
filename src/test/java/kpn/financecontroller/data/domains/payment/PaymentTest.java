package kpn.financecontroller.data.domains.payment;

// TODO: 18.07.2022 restore
//import kpn.financecontroller.data.domains.seller.Seller;
//import kpn.financecontroller.data.domains.product.Product;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvFileSource;
//import org.mockito.Mockito;
//
//import java.time.LocalDate;
//import java.util.ArrayDeque;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class PaymentTest {
//
//    @ParameterizedTest
//    @CsvFileSource(resources = "shouldCheckGetting.csv")
//    void shouldCheckGetting(Long id,
//                            String productAnswer,
//                            float price,
//                            Currency currency,
//                            float amount,
//                            Measure measure,
//                            String sellerAnswer,
//                            String rawLocalDate,
//                            String rawPath,
//                            String expectedResult) {
//        ArrayDeque<String> path = new ArrayDeque<>(List.of(rawPath.split("\\.")));
//        Payment domain = new Payment();
//        domain.setId(id);
//        domain.setProduct(createProduct(productAnswer));
//        domain.setPrice(price);
//        domain.setCurrency(currency);
//        domain.setAmount(amount);
//        domain.setMeasure(measure);
//        domain.setSeller(createSeller(sellerAnswer));
//        domain.setCreatedAt(LocalDate.parse(rawLocalDate));
//
//        String result = domain.get(path);
//        assertThat(expectedResult).isEqualTo(result);
//    }
//
//    private Product createProduct(String productAnswer) {
//        Product product = Mockito.mock(Product.class);
//        Mockito
//                .when(product.get(Mockito.any()))
//                .thenReturn(productAnswer);
//        return product;
//    }
//
//    private Seller createSeller(String placeAnswer) {
//        Seller seller = Mockito.mock(Seller.class);
//        Mockito
//                .when(seller.get(Mockito.any()))
//                .thenReturn(placeAnswer);
//        return seller;
//    }
//}