package kpn.financecontroller.data.service;

public interface Deleter<D, E, I>{
    void byId(I id);
    void by(String attribute, Object value);
    void all();
}
