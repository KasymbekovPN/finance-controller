package kpn.financecontroller.gui.status;

public interface AttributeProcessor<STATUS, ATTRIBUTE> {
    void setStatus(STATUS status);
    STATUS getStatus();
    void calculate(ATTRIBUTE attribute);
    void clear(ATTRIBUTE attribute);
}
