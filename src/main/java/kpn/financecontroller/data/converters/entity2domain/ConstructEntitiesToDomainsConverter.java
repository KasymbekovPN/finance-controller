// TODO: 19.07.2022 del
//package kpn.financecontroller.data.converters.entity2domain;
//
//import kpn.financecontroller.data.domains.Domain;
//import kpn.financecontroller.data.entities.AbstractEntity;
//import lombok.RequiredArgsConstructor;
//
//import java.util.List;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//final public class ConstructEntitiesToDomainsConverter<ENTITY extends AbstractEntity, DOMAIN extends Domain>
//        implements Function<List<ENTITY>, List<DOMAIN>> {
//    private final Function<ENTITY, DOMAIN> creator;
//
//    @Override
//    public List<DOMAIN> apply(List<ENTITY> entities) {
//        return entities.stream().map(creator).collect(Collectors.toList());
//    }
//}
