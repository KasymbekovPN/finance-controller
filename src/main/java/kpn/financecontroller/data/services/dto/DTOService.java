package kpn.financecontroller.data.services.dto;

import com.querydsl.core.types.Predicate;

public interface DTOService<DOMAIN, ENTITY> extends BaseDTOService<DOMAIN, ENTITY, Long, Predicate> {}
