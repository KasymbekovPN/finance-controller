package kpn.financecontroller.gui.binding.wrapper;

import kpn.financecontroller.data.services.dto.service.*;
import kpn.financecontroller.gui.external.service.AddressServiceWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public final class WrappersBindingBPP implements BeanPostProcessor {
    private static final Set<Class<?>> ALLOWED_CLASSES = Set.of(
            CountryDtoDecorator.class,
            RegionDtoDecorator.class,
            CityDtoDecorator.class,
            StreetDtoDecorator.class,
            AddressDtoDecorator.class,
            SellerDtoDecorator.class,
            TagDtoDecorator.class,
            ProductDtoDecorator.class,
            PaymentDtoDecorator.class
    );

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (checkBean(bean)){
            AddressServiceWrapper.registerService(bean);
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    private boolean checkBean(Object bean) {
        return ALLOWED_CLASSES.contains(bean.getClass());
    }
}
