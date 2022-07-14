package kpn.financecontroller.data.services.dto;

import lombok.Getter;

// TODO: 14.07.2022 del
@Getter
public class DTOServiceExceptionOld extends Exception{

    private final Object[] args;

    public DTOServiceExceptionOld(String message, Object... args) {
        super(message);
        this.args = args;
    }
}
