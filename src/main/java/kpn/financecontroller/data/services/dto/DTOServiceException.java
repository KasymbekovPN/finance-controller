package kpn.financecontroller.data.services.dto;

import lombok.Getter;

@Getter
public class DTOServiceException extends Exception{

    private final Object[] args;

    public DTOServiceException(String message, Object... args) {
        super(message);
        this.args = args;
    }
}
