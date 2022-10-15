package kpn.financecontroller.gui.binding.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.function.Function;

public abstract class BaseEventBindingBPP implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        createChainLink().start(bean);
        bind();
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    protected void bind() {
        if (checkBindingCondition()){
            doBinding();
            reset();
        }
    }

    protected void doBinding() {
    }

    protected void reset() {
    }

    protected abstract ChainLink createChainLink();
    protected abstract boolean checkBindingCondition();

    @RequiredArgsConstructor
    protected static class ChainLink {
        private final Function<Object, Boolean> setter;
        private ChainLink next;
        public void start(Object bean) {
            if (!setter.apply(bean) && next != null){
                next.start(bean);
            }
        }

        public ChainLink addNext(Function<Object, Boolean> setter){
            if (next == null){
                next = new ChainLink(setter);
            } else {
                next.addNext(setter);
            }
            return this;
        }
    }
}
