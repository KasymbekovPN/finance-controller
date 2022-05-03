package kpn.financecontroller.data.services.statistic.byTag;

import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.data.domains.place.Place;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.data.entities.payment.PaymentEntity;
import kpn.financecontroller.data.entities.place.PlaceEntity;
import kpn.financecontroller.data.entities.product.ProductEntity;
import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.data.services.dto.DTOService;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;


@SpringBootTest
@ActiveProfiles("test")
class ByTagStatisticServiceImplTest {

    @Autowired
    private DTOService<Tag, TagEntity, Long> tagService;
    @Autowired
    private DTOService<Place, PlaceEntity, Long> placeService;
    @Autowired
    private DTOService<Product, ProductEntity, Long> productService;
    @Autowired
    private DTOService<Payment, PaymentEntity, Long> paymentService;

    @Test
    void shouldDoSth() {
        Result<List<Tag>> tagResult = tagService.loader().all();
        Result<List<Place>> placeResult = placeService.loader().all();
        Result<List<Product>> productResult = productService.loader().all();
        Result<List<Payment>> paymentResult = paymentService.loader().all();
    }
}