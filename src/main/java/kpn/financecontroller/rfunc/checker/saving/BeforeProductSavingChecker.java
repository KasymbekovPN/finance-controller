package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domains.product.Product;
import org.springframework.stereotype.Component;

@Component
final public class BeforeProductSavingChecker extends AbstractBeforeSavingChecker<Product> {}
